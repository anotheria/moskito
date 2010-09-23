			/****************************************/
			/****** Attribute ***********************/
			/****************************************/
			function Attribute(val) {
				this.value = val;
			}
			Attribute.prototype.onchange = function(v) {
				this.value = v;
			}
			Attribute.prototype.toString = function() {
				return this.value + "";
			}

			/****************************************/
			/****** ViewConfiguration ***************/
			/****************************************/
			
			function ViewConfiguration(name) {
				this.name = new Attribute(name);
				this.fields = [];
			}
			ViewConfiguration.prototype.addField = function(viewField) {
				this.fields[this.fields.length] = viewField;
				viewField.view = this;
				viewField.index = this.fields.length;
			}
			ViewConfiguration.prototype.toString = function() {
				return this.name.value;
			}
			ViewConfiguration.prototype.getKeyName = function() {
				return "view:"+this.name;
			}
			ViewConfiguration.prototype.printData = function(winObj) {
				// clear content before reusing
				winObj.innerHTML = "";
				try {
					var view = this;
					var border = createElement("div", {"class":"view"});
					var label = createElement("div", {"class":"label"});
					label.appendChild(document.createTextNode("View Name :"));
					border.appendChild(label);
					{
						var addDiv = document.createElement("div");
						addDiv.innerHTML = "Add";
						addDiv.className = "add_field";
						addDiv.onclick = function() {
							//show(getById("available_fields"));
							view.addSelectedAvailableFields();
						}
						border.appendChild(addDiv);
					}
	
					var viewname = document.createElement("div");
					viewname.className = "view_name";
					var name = createElement("input", {
						"id":"view_name",
						"type":"text",
						"name":this.getKeyName(),
						"class": "viewname"
					});
					
					name.setAttribute("value", view.name.value);
					name.onchange = function() {
						view.name.onchange(this.value);
						currentView = this.value;
						viewAllViews();
					}
					viewname.appendChild(name);
					border.appendChild(viewname);
					
					winObj.appendChild(border);
					
					// forming table with fields as columns
					
					var table = createElement("table", {
						"cellpadding":"0", 
						"cellspacing":"0", 
						"border":"0", 
						"width":"100%",
						"style": "border: 0px solid red;",
					});
					var tbody = createElement("tbody");
					table.appendChild(tbody);
					
					var tr = createElement("tr");
					tbody.appendChild(tr);
					
					for (var i=0; i<view.fields.length; i++) {
						var field = view.fields[i];
						var td = createElement("td");
						field.printField(td);
						tr.appendChild(td);
					}
					
					//var td = createElement("td");
					//td.appendChild(document.createTextNode("dima"));
					//tr.appendChild(td);
					
					winObj.appendChild(table);
	
					//block with fields
					/*
					var fields = document.createElement("div");
					for (var i=0; i<view.fields.length; i++) {
						var field = view.fields[i];
						var fieldBox = document.createElement("div");
						fieldBox.style.cssText = "border: 1px solid red;";
						field.printField(fieldBox);
						fields.appendChild(fieldBox);
					}
					border.appendChild(fields);
					*/

					

				} catch (e) {alert("Error:" + e);}
			}

			ViewConfiguration.prototype.printOnPage = function(boxObj) {
				var divView = document.createElement("div");
				divView.className = "view";

				{
					var edit = document.createElement("div");
					edit.className = "delete";
					edit.innerHTML = "Delete";
					edit.onclick = new Function("deleteView('" + this.name.value + "')");
					divView.appendChild(edit);
				}
				{
					var label = document.createElement("div");
					label.className = "label";
					label.innerHTML = "View name :&nbsp;";
					divView.appendChild(label);

					var value = document.createElement("div");
					value.className = "view_name";
					value.appendChild(document.createTextNode(this.name.value));
					divView.appendChild(value);
				}
				{
					var label = document.createElement("div");
					label.className = "label";
					label.innerHTML = "Fields size :&nbsp;";
					divView.appendChild(label);

					var value = document.createElement("div");
					value.className = "fields_length";
					value.appendChild(document.createTextNode(this.fields.length));
					divView.appendChild(value);
				}
				{
					var edit = document.createElement("div");
					edit.className = "edit";
					edit.innerHTML = "Edit...";
					edit.onclick = new Function("editView('" + this.name.value + "')");
					divView.appendChild(edit);
				}
				
				
				boxObj.appendChild(divView);
				boxObj.appendChild(document.createElement("br"));
			}

			ViewConfiguration.prototype.moveFieldDown = function(field) {
				for(var i=0; i<this.fields.length; i++) {
					var f = this.fields[i];
					if (f.attributeName == field.attributeName) {
						if (i == this.fields.length-1) {
							break;
						}
						var prev = this.fields[i+1];
						var curr = f;
						this.fields[i] = prev;
						this.fields[i+1] = curr;
						break;
					}
				}
				this.printData(getById('winId'));
			}
			ViewConfiguration.prototype.moveFieldUp = function(field) {
				for(var i=0; i<this.fields.length; i++) {
					var f = this.fields[i];
					if (f.attributeName == field.attributeName) {
						if (i == 0) {
							break;
						}
						var prev = this.fields[i-1];
						var curr = f;
						this.fields[i] = prev;
						this.fields[i-1] = curr;
						break;
					}
				}
				this.printData(getById('winId'));
			}

			ViewConfiguration.prototype.addSelectedAvailableFields = function() {
				var boxObj = getById('available_fields');
				boxObj.innerHTML = "";

				var hash = {};
				for (var k=0; k<this.fields.length; k++) {
					var viewField = this.fields[k];
					hash[viewField.path.value] = viewField;
				}
				availableColumns.sort(function (a, b) {
					if (a.attributeName.value == b.attributeName.value) {
						return 0;
					}
					if (a.attributeName.value > b.attributeName.value) {
						return 1;
					}
					if (a.attributeName.value < b.attributeName.value) {
						return -1;
					}
				});
				for (var i=0; i<availableColumns.length; i++) {
					var field = availableColumns[i];

					if (!hash[field.path.value] || this.fields.length == 0) {
						var fieldBox = document.createElement("div");
						fieldBox.style.cssText = "border: 1px solid red;";
						boxObj.appendChild(fieldBox);
						field.printAvailableField(fieldBox);
					}
					
				}
				show(getById('fields'));
			}

			ViewConfiguration.prototype.deleteField = function(delF) {
				for (var i=0; i<this.fields.length; i++) {
					var field = this.fields[i];
					if (field.attributeName == delF.attributeName) {
						this.fields.splice(i, 1);
						this.printData(getById('winId'));
						viewAllViews();
						break;
					}
				}
			}
			
			/****************************************/
			/****** ViewField ***********************/
			/****************************************/
			function ViewField() {
				
				this.view = new Object();
				this.index = -1;
				
				this.fieldName 			= new Attribute(arguments[0]);
				this.attributeName 		= new Attribute(arguments[1]);
				this.type 				= new Attribute(arguments[2]);
				this.javaType 			= new Attribute(arguments[3]);
				this.visible 			= new Attribute(arguments[4]);
				this.path 				= new Attribute(arguments[5]);
				this.total 				= new Attribute(arguments[6]);
				this.guard 				= new Attribute(arguments[7]);
				this.format 			= new Attribute(arguments[8]);
			}

			ViewField.prototype.toString = function() {
				return this.attributeName.value;
			}

			ViewField.prototype.down = function() {
				this.view.moveFieldDown(this);
			}

			ViewField.prototype.up = function() {
				this.view.moveFieldUp(this);
			}

			ViewField.prototype.printAvailableField = function(boxObj) {
				var checkboxId = "avail_"+this.attributeName;
				var checkbox = document.createElement("input");
				checkbox.setAttribute("type", "checkbox");
				checkbox.setAttribute("id", checkboxId);
				checkbox.style.cssText = "float: left;";
				boxObj.appendChild(checkbox);
				var label = document.createElement("label");
				label.appendChild(document.createTextNode(this.fieldName));
				label.setAttribute("for", checkboxId);
				label.style.cssText = "cursor: pointer;";
				boxObj.appendChild(label);
			}
			
			ViewField.prototype.printField = function(boxObj) {

				function attr(boxObj, fieldN, fieldV, attributes) {
					
					//var fieldLabel = createElement("div", {
					//	"style":"width: 140px;",
					//	"class":"label",
					//});
					//fieldLabel.innerHTML = fieldN + " :&nbsp;"
					//boxObj.appendChild(fieldLabel);

					var fieldValue = document.createElement("div");
					fieldValue.className = "field_value";
					var input = createElement("input", {
						"type":"text",
						"value":fieldV.value,
					});
					input.onchange = function() {
						fieldV.onchange(this.value);
					};

					for (var attribute in attributes) {
						input.setAttribute(attribute, attributes[attribute]);
					}
					
					fieldValue.appendChild(input);
					boxObj.appendChild(fieldValue);
				}
				
				var field = this;

				var del = document.createElement("div");
				del.style.cssText = "float: right; display: block; border: 1px solid green; cursor: hand;";
				del.innerHTML = "delete";
				del.onclick = function() {
					field.deleteField();
				}
				boxObj.appendChild(del);
				
				attr(boxObj, "fieldName", this.fieldName, {"size": 20, "class": "fieldName"});
				attr(boxObj, "attributeName", this.attributeName, {"size": 20, "class": "attributeName"});
				attr(boxObj, "type", this.type, {"size": 10, "class": "type"});
				attr(boxObj, "javaType", this.javaType, {"size": 20, "class": "javaType"});
				attr(boxObj, "visible", this.visible, {"size": 5, "class": "visible"});
				attr(boxObj, "path", this.path, {"size": 50, "class": "path"});
				attr(boxObj, "total", this.total, {"size": 20, "class": "total"});
				attr(boxObj, "guard", this.guard, {"size": 50, "class": "guard"});
				attr(boxObj, "format", this.format, {"size": 10, "class": "format"});
				
				var buttons = document.createElement("div");
				
				var down = document.createElement("div");
				down.style.cssText = "float: right; display: block; border: 1px solid green; cursor: hand;";
				down.innerHTML = "Right";
				down.onclick = function() {
					field.down();
				}
				

				var up = document.createElement("div");
				up.style.cssText = "float: right; display: block; border: 1px solid green; cursor: hand;";
				up.innerHTML = "Left";
				up.onclick = function() {
					field.up();
				}
				
				
				buttons.appendChild(down);
				buttons.appendChild(up);

				var space = document.createElement("div");
				space.innerHTML = "&nbsp;";
				buttons.appendChild(space);
				
				boxObj.appendChild(buttons);
				
			}
			
			ViewField.prototype.deleteField = function() {
				this.view.deleteField(this);
			}

			
			function getById(id) {
				return document.getElementById(id);
			}
			function editView(viewName) {
				
				getById('winId').innerHTML = "";
				for (var i=0; i<views.length; i++) {
					var tmpView = views[i];
					if (viewName == tmpView.name) {
						tmpView.printData(getById('winId'));
						//show(getById('props'));
					}
				}
				//hide(getById('fields'));
				//getById('available_fields').innerHTML = "";
			}
			function show(win) {
				win.style.display = "block";
			}
			function hide(win) {
				win.style.display = "none";
			}
			function viewAllViews() {
				// clear content before reusing
				getById('current_config').innerHTML = "";
				for (var i = 0; i < views.length; i++) {
					var view = views[i];
					view.printOnPage(getById('current_config'));
				}
			}


			// TODO
			// 1. add view field (go first)
			function addView(currConfig) {
				views[views.length] = new ViewConfiguration("View " + viewsCounter++ );
				viewAllViews();
			}

			// 1.1. remove view field
			function deleteView(viewName) {
				for (var i=0; i<views.length; i++) {
					var tmpView = views[i];
					if (viewName == tmpView.name) {
						//views[i] = null;
						views.splice(i, 1);
						break;
					}
				}
				viewAllViews();
			}

			function addFields() {
				var view_name = getById('view_name');
				var view = null;
				for (var i=0; i<views.length; i++) {
					if (views[i].name == view_name.value) {
						view = views[i];
					}
				}
				for (var i=0; i<availableColumns.length; i++) {
					var field = availableColumns[i];
					var checkBox = getById('avail_' + field.attributeName);
					if (view && checkBox && checkBox.checked) {
						view.addField(field);
					}
				}
				view.printData(getById('winId'));
				viewAllViews();
				hide(getById('fields'));
				getById('available_fields').innerHTML = "";
			}

			// 2. add create new view field
			
			// 2.1. remove view field
			
			function generate() {
				getById('output').innerHTML = "";
				show(getById('output'));
				println("views : [");
				
				for (var i=0; i<views.length; i++) {
					var view = views[i];
					println("{");
					println("name : \"" + view.name.value + "\",");
					println("columns : [");
					for (var k=0; k<view.fields.length; k++) {
						var column = view.fields[k];
						println("{");
						println("attribute: \"" + column.attributeName.value + "\",");
						println("name: \"" + column.fieldName.value + "\",");
						println("type: \"" + column.type.value + "\",");
						println("class: \"" + column.javaType.value + "\",");
						println("visible: " + column.visible.value + ",");
						println("path: \"" + column.path.value + "\",");
						println("guard: \"" + column.guard.value + "\",");
						println("total: \"" + column.total.value + "\",");
						println("format: \"" + column.format.value + "\",");
						println("},");
					}
					println("]},");
				}
				println("]");
				var form = getById('saving_form');
				var json = getById('json');
				json.value = getById('output').innerHTML;
				form.submit();
			}

			function println(str) {
				//var obj = document.createElement('div');
				//obj.appendChild(document.createTextNode(str));
				
				getById('output').appendChild(document.createTextNode(str));
				getById('output').innerHTML = getById('output').innerHTML + "\n";
			}
			
			function createElement(elemName, attributes) {
				var elem = document.createElement(elemName);
				for (var attribute in attributes) {
					elem.setAttribute(attribute, attributes[attribute]);
				}
				return elem;
			}