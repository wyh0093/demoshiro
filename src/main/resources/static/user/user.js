/**
 * Created by Administrator on 2016/8/4.
 */


function exportData(){
    location.href=contextPath+"user/export?keyword="+$("#name").val()+"&status="+$("#statusId").val();
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
    userId = id;
    layer.open({
        type: 2,
        title: '用户信息',
        shadeClose: true,
        shade: 0.5,
        skin: 'layui-layer-rim',
        closeBtn: 2,
        area: ['80%', '90%'],
        shadeClose: true,
        closeBtn: 2,
        content: './user/User_Tail.html'

    });

}

function openPermission(id) {
    roleId = id;
    layer.open({
        type: 2,
        title: '分配权限',
        shadeClose: true,
        shade: 0.5,
        skin: 'layui-layer-rim',
        closeBtn: 2,
        area: ['50%', '80%'],
        shadeClose: true,
        closeBtn: 2,
        content: './role/Role_Permission.html'

    });

}

function del(id){
    layer.confirm('确定要删除用户么？', {
        btn: ['确定','取消'] //按钮
    }, function(){
        layer.closeAll('dialog');
        $.ajax({
            type: "POST",
            url: contextPath+"user/del",
            dataType: "text",
            data: { uid:id },
            success: function (result) {
                if (result=='success') {
                    successAlert('删除成功!');
                }
            }
        });
    });
}




