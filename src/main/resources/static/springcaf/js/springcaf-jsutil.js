/**
 * Confirm JS action
 * @param action
 * @returns
 */
function confirmforaction(action)
{
	var message = "Are you sure you want to " + action + " the selected item(s)?";
	var result = confirm(message);
	
	return result;
};

/**
 * Script used to manage Dropdown dependencies
 * @param base_rest_url
 * @param element_id
 * @returns
 */
function elementvaluechanged(base_rest_url, element_id)
{
	var field_name = element_id;
	var value = document.getElementById(field_name).value;
	var rest_url = base_rest_url + '/' + element_id + '/' + value;
	
	var jqxhr = $.ajax( rest_url )
		.done(function(data) {
			JSON.parse(data, function (field_id, field_value) {
				var current_element = document.getElementById(field_id);
				if(current_element)
				{
					if(current_element.tagName.toUpperCase() == 'SELECT')
					{
						current_element.innerHTML = field_value;
					}
					else if(current_element.tagName.toUpperCase() == 'INPUT')
					{
						current_element.value = field_value;
					}
				} 
			});
		})
		.fail(function() {
			alert( "dropdown reload failed" );
		});

	return false;
}

function registerDropdownChangeEvent(dropdown_element_id, action_parm_key)
{
    $("#" + dropdown_element_id).change(function () {
        var val = this.value;
        var currentUrl = window.location.href;

        if (val === null || val == '') {
            val = 'UA';
        }
        var changeUrl = currentUrl.split('?')[0] + '?' + action_parm_key + '=' + val;
        window.location.href = changeUrl;
    });
}

function textInputAutoComplete(text_input_element_id, source_url)
{
    $('#' + text_input_element_id).autocomplete({
        source: function(request, response) {
  	        $.ajax({
  	            url: source_url,
  	            dataType: "json",
  	            data: {
  	                name : request.term
  	            },
  	            success: function(data) {
  	                response(data);
  	            },
                error: function(jqXHR, textStatus, errorThrown){
                    alert("Session timed out. Please refresh the page to login again");                        
                }
  	        });
  	    },
  	    minLength: 2,
  	    delay: 300,
        select: function( event, ui ) {
            $('#' + text_input_element_id).val( ui.item.label );
            window.location.href = ui.item.url
            return false;
        }
    });
}
