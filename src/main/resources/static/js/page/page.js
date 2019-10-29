function fenyeInit(id,currentPage,totalPage,totalCount){
    document.getElementById("curPage").innerText=currentPage;
    document.getElementById("totalPage").innerText=totalPage;
    document.getElementById("totalCount").innerText=totalCount;
    $('#previousPage').removeClass('disabled');
    $('#nextPage').removeClass('disabled');
    var ulHtml = "";
    ulHtml+="<li id='previousPage' onclick='query("+(currentPage-1)+")'><a href='#'>&laquo;</a></li>";
    if((totalPage-currentPage)>=5){
        ulHtml+="<li class='active'><a href='#' onclick='ulClick()'>"+currentPage+"</a></li>";
        ulHtml+="<li><a href='#' onclick='ulClick()'>"+(currentPage+1)+"</a></li>";
        ulHtml+="<li><a href='#' onclick='ulClick()'>"+(currentPage+2)+"</a></li>";
        ulHtml+="<li><a href='#' onclick='ulClick()'>"+(currentPage+3)+"</a></li>";
        ulHtml+="<li><a href='#' onclick='ulClick()'>"+(currentPage+4)+"</a></li>";
        ulHtml+="<li><a href='#'>...</a></li>";
    }else{
        if(currentPage>1){
            ulHtml+="<li><a href='#' onclick='ulClick()'>...</a></li>";
        }
        for(var i=currentPage;i<=totalPage;i++){
            if(i==currentPage){
                ulHtml+="<li class='active'><a href='#' onclick='ulClick()'>"+i+"</a></li>";
            }else{
                ulHtml+="<li><a href='#' onclick='ulClick()'>"+i+"</a></li>";
            }
        }

    }
    ulHtml+="<li id='nextPage' onclick='query("+(currentPage+1)+")'><a href='#'>&raquo;</a></li>";
    ulHtml+="<input style='margin-left: 20px;width: 90px' type='text' placeholder='请输入页数' id='inputPage'  class='input-sm'> 页 <button class='tabe_btn ' onclick='go()'>GO</button>";
    var ulPage = document.getElementById(id);
    ulPage.innerHTML=ulHtml;
    if(currentPage<=1){
        $('#previousPage').addClass('disabled');
    }
    if(currentPage>=totalPage){
        $('#nextPage').addClass('disabled');
    }
}

function ulClick() {
    var obj_lis = document.getElementById("pageUl").getElementsByTagName("li");
    for(i=0;i<obj_lis.length;i++){
        obj_lis[i].onclick = function(){
            if(this.className=='disabled'){
                return;
            }
            var number = this.getElementsByTagName('a')[0].innerHTML;
            query(number);
        }
    }
}