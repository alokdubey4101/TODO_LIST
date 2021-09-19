/**
 * 
 */
$(document).ready(function(){
	
	populate();
	
	
		
	$('.but').click(function(){
		
		
		
		var fi=$('.pal').val();
		if(fi.trim()===''){
			
		}
		else{
			var json={todo:fi};
			var jsons=JSON.stringify(json);
			$.ajax({
				type:'POST',
				url:'todo/event/add',
				data:jsons,
				contentType:'application/json',
				success:function(result){
					if(result.status==='OK'){
						$('.pal').val('');
						alert(result.message);
						populate();
					}
					else{
						
					}	
					
				}
				
			});	
			
		}
		
	});
});

function populate(){
	$('.show').html('<center><img class="oly" src="./resource/dimg/No trespassing.gif"/></center>');
	$.ajax({
		type:'GET',
		url:'todo/event/getAll',
		contentType:'application/json',
		success:function(result){
			if(result.length<0){
				$('.show').html('<h6>TodoList is Empty</h6>');
			}else{
				var op=[];
				var ops=[];
				var j=0;
				$.each(result, function(i, data){
					op[i]='<div class="container main"><div class="jumbotron enter">'+data.todo+'</h6><input type="hidden" class="id" value="'+data.todoid+'"/></div></div><br>';
				});
				for(var i=op.length; i>=0; i--){
					j++;
					ops[j]=op[i];
				}
				$('.show').html(ops);
			}
		}
	});
}