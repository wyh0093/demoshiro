/**
 * Created by Administrator on 2016/8/4.
 */


function exportData(){
    location.href=contextPath+"depart/export?keyword="+$("#name").val();
}

function openRole(id) {
    userId = id;
    layer.open({
        type: 2,
        title: '分配角色',
        shadeClose: true,
        shade: 0.5,
        skin: 'layui-layer-rim',
        closeBtn: 2,
        area: ['50%', '80%'],
        shadeClose: true,
        closeBtn: 2,
        content: './user/User_Role.html'

    });

}

function openlayer(value,id) {
    operate = value;
    departId = id;
    layer.open({
        type: 2,
        title: '部门信息',
        shadeClose: true,
        shade: 0.5,
        skin: 'layui-layer-rim',
        closeBtn: 2,
        area: ['60%', '70%'],
        shadeClose: true,
        closeBtn: 2,
        content: './department/Department_Tail.html'

    });

}

function del(id){
    layer.confirm('确定要删除部门么？', {
        btn: ['确定','取消'] //按钮
    }, function(){
        layer.closeAll('dialog');
        $.ajax({
            type: "POST",
            url: contextPath+"depart/delete",
            dataType: "text",
            data: { id:id },
            success: function (result) {
                if (result=='success') {
                    successAlert('删除成功!');
                }
            }
        });
    });
}




