/**
 * 支付宝转账确认页面js
 */
$(function(){
	// 切换事件
	$("#order-container .nav li").on("click", function(event){
		var _this = this;
		$("#order-container .nav li").each(function(i){
			$(this).removeClass("active");
			if(_this == this) {
				if(i == 0) {
					renderTable('#unpaidTable');
				} else if(i == 1) {
					renderTable('#partpaidTable');
				} else if(i == 2) {
					renderTable('#paidTable');
				}
				$("#order-container .table-box div.box").each(function(innerI) {
					this.className = "box unactive";
				});
				$($("#order-container .table-box div.box")[i]).removeClass("unactive");
				$($("#order-container .table-box div.box")[i]).addClass("active");
			}
		});
		$(this).addClass("active");
	});
	// 加载数据
	function retrieveData(sSource, aoData, fnCallback, oSettings) {
		var draw = (aoData[3].value / 10) + 1;
		oSettings.iDraw = draw;
		// 排序
		var sorts = [];
		var orderField = null;
		var orderDir = null;
		if(aoData[2].value.length > 0) {
			var orderIndex = aoData[2].value[0].column;
			if(orderIndex == 0) {
				orderField = "paymentNo";
			} else if(orderIndex == 1) {
				orderField = "publisherId";
			} else if(orderIndex == 2) {
				orderField = "alipayAccount";
			} else if(orderIndex == 3) {
				orderField = "amount";
			} else if(orderIndex == 4) {
				orderField = "createTime";
			}
			orderDir = aoData[2].value[0].dir;
			if(orderField) {
				sorts.push({
					"field": orderField,
					"dir": orderDir
				});
			}
		}
		// 搜索
		var keyword = aoData[5].value.value;
		
		var state = "Unpaid";
		if(oSettings.sTableId == "unpaidTable") {
			state = "Unpaid";
		} else if(oSettings.sTableId == "partpaidTable") {
			state = "PartPaid";
		} else if(oSettings.sTableId == "paidTable") {
			state = "Paid";
		}
		var query = {
			"page": draw - 1,
			"states": [state],
			"types": ["AliTurnPay"],
			"sorts": sorts,
			"keyword": keyword
		};
		$.ajax({
            type: "POST",
            url: "../aliturn/pages",
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify(query),
            success: function (jsonResult) {
            	var dtData = {
            		"draw": draw,
          			"recordsTotal": jsonResult.result.totalElements,
					"recordsFiltered": jsonResult.result.totalElements,
					"data": jsonResult.result.content
            	};
            	fnCallback(dtData);
            }
        });
	}
	// 渲染表格
	function renderTable(id) {
		if($(id + "_wrapper").length > 0) {
			$(id).dataTable().fnDraw();
		} else {
			var columns = [
	            { "data": "paymentNo", "title": "支付单号", "className": "paymentno-cell"},
	            { "data": "publisherId", "title": "发布人ID" },
	            { "data": "alipayAccount", "title": "用户支付宝账号" },
	            { "data": "amount", "title": "支付金额" },
	            { "data": "createTime", "title": "下单时间" },
	            { "orderable": false, "width": 200,"title": "操作", "render": function(data, type, full, meta) {
	                return "<button class='confirm-paid-btn' amount='" + full.amount + "' paymentno='" + full.paymentNo + "'>确认支付</button> &nbsp;&nbsp; <button class='confirm-partpaid-btn' amount='" + full.amount + "' paymentno='" + full.paymentNo + "'>确认部分支付</button>";
	            }}
	        ];
			if(id == "#partpaidTable") {
				columns = [
		            { "data": "paymentNo", "title": "支付单号", "className": "paymentno-cell"},
		            { "data": "publisherId", "title": "发布人ID" },
		            { "data": "alipayAccount", "title": "用户支付宝账号" },
		            { "data": "amount", "title": "支付金额" },
		            { "data": "createTime", "title": "下单时间" },
		            { "orderable": false, "width": 200, "title": "操作", "render": function(data, type, full, meta) {
		                return "<button class='confirm-partpaid-btn' amount='" + full.amount + "' paymentno='" + full.paymentNo + "'>确认部分支付</button>";
		            }}
		        ];
			} else if(id == "#paidTable") {
				columns = [
		            { "data": "paymentNo", "title": "支付单号", "className": "paymentno-cell"},
		            { "data": "publisherId", "title": "发布人ID" },
		            { "data": "alipayAccount", "title": "用户支付宝账号" },
		            { "data": "amount", "title": "支付金额" },
		            { "data": "createTime", "title": "下单时间" }
		        ]
			};
			$(id).dataTable({
				"responsive": true,
		        "processing": true,
		        "serverSide": true,
		        "bPaginate": true,
		        "dom": "<'row'<'col-sm-6'><'col-sm-6'f>><'row'<'col-sm-12'tr>><'row'<'col-sm-3'i><'col-sm-9'p>>",
		        "fnServerData": retrieveData,
		        "columns": columns,
		        "aaSorting": [[ 4, "desc" ]],
		        "oLanguage": {                        //汉化     
	                "sLengthMenu": "每页显示 _MENU_ 条记录",
	                "sSearch": '<span>搜索：</span>',
	                "sZeroRecords": "没有检索到数据",     
	                "sInfo": "当前数据为从第 _START_ 到第 _END_ 条数据；总共有 _TOTAL_ 条记录",     
	                "sInfoEmtpy": "没有数据",     
	                "sProcessing": "正在加载数据...",     
	                "oPaginate": {     
	                    "sFirst": "首页",     
	                    "sPrevious": "前页",     
	                    "sNext": "后页",     
	                    "sLast": "尾页"    
	                }     
	            }
		    });
		}
	}
	// 渲染数据
	renderTable('#unpaidTable');
	renderTable('#partpaidTable');
	renderTable('#paidTable');
	// 确认支付
	$("#unpaidTable_wrapper").on("click", ".confirm-paid-btn", function(event){
		var paymentNo = $(this).attr("paymentno");
		var _this = this;
		layer.confirm('确定已收到用户支付宝转账' + $(_this).attr("amount") + '元？', {
			btn : [ '确定', '取消' ]
		}, function(index) {
			layer.close(index);
			$.ajax({
	            type: "POST",
	            url: "../aliturn/paid",
	            contentType: "application/x-www-form-urlencoded",
	            dataType: "json",
	            data: "paymentNo=" + paymentNo,
	            success: function (jsonResult) {
	            	if(jsonResult.code != "200") {
	            		layer.msg(jsonResult.message);
	            	} else {
	            		renderTable('#unpaidTable');
	            	}
	            }
	        });
		});
	});
	// 确认部分支付
	$("#unpaidTable_wrapper, #partpaidTable_wrapper").on("click", ".confirm-partpaid-btn", function(event){
		var paymentNo = $(this).attr("paymentno");
		layer.prompt({
			title : '输入用户支付宝转账的金额',
			formType : 4
		}, function(money, index) {
			var reg = /(^[1-9]([0-9]+)?(\.[0-9]{1,2})?$)|(^(0){1}$)|(^[0-9]\.[0-9]([0-9])?$)/;
			if (reg.test(money)) {
				layer.close(index);
				$.ajax({
		            type: "POST",
		            url: "../aliturn/partpaid",
		            contentType: "application/x-www-form-urlencoded",
		            dataType: "json",
		            data: "paymentNo=" + paymentNo + "&partAmount=" + money,
		            success: function (jsonResult) {
		            	if(jsonResult.code != "200") {
		            		layer.msg(jsonResult.message);
		            	} else {
		            		renderTable('#unpaidTable');
		            	}
		            }
		        });
		    } else {
		    	layer.msg('金额格式有误，最多为两位小数的数字', {time: 1000});
		    };
		});
	});
});