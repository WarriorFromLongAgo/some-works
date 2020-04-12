
//分页的显示与隐藏
$(".xifenye").click(function(a){
    var ftype = $(this).parents('.fenye').data('type');
    var uljia=$(this).children('.xab').children('.uljia');
    uljia.empty();
    $(this).children('.xab').toggle();
	//显示出的一共多少页

	var page=parseInt($(this).children('.xiye').html());//获取当前的页数
	var pages=parseInt($(this).children('.mo').html());//获取当前的总页数
	for(var i=1;i<=pages;i++)
	{
		var H="<li  onclick='fl("+i+",\""+ftype+"\",\"" + $(this).parents('.fenye').attr('id') + "\")'>"+i+"</li>";//添加一共多少页和点击事件
		
		uljia.append(H);
	}
	scrolltop(page);
})
//点击分页显示的方法
function fl(p1,p2,d){
    var v=$('#'+d).children('ul').children('.xifenye').children('.xiye');
    v.empty();
    v.html(p1);//给显示的页数赋值
    getTaskList(parseInt(p1)-1,p2);
}
//分页的的上一页和下一页
function topclick(that){
    var v=that.parents('.fenye').children('ul').children('.xifenye').children('.xiye');
    var num=parseInt(v.html());
	if(num>1)
	{
		num--;
        v.html(num);
        scrolltop(num);
		getTaskList(parseInt(num)-1,that.parents('.fenye').data('type'));
	}
}
function downclick(that){
	var pages=parseInt(that.parents('.fenye').children('ul').children('.xifenye').children(".mo").html());//获取当前的总页数
    var v=that.parents('.fenye').children('ul').children('.xifenye').children('.xiye');
	var num=parseInt(v.html());
	if(num < pages){
		num = ++num;
		v.html(num);
		scrolltop(num);
		getTaskList(parseInt(num)-1,that.parents('.fenye').data('type'));
    }
}
//分页的的首页和未页
$(".first").bind("click",function(){
	var v=$(this).parents('.fenye').children('ul').children('.xifenye').children('.xiye');
	v.html(1);
	scrolltop(v.html());
	getTaskList(parseInt(v.html())-1,$(this).parents('.fenye').data('type'));
	})
$(".last").bind("click",function(){
	var v=$(this).parents('.fenye').children('ul').children('.xifenye').children('.xiye');
	var l=$(this).parents('.fenye').children('ul').children('.xifenye').children('.mo');
	v.html(l.html());
	scrolltop(v.html());
	getTaskList(parseInt(v.html())-1,$(this).parents('.fenye').data('type'));
	})
//滚动条
function scrolltop(top){
	var hei=25*top-25;
	$(".xab").scrollTop(hei);
	}