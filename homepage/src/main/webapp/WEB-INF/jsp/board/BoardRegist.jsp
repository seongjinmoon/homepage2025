<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<c:import url="/template/header.do" charEncoding="utf-8">
	<c:param name="title" value="게시판"/>
</c:import>

<%-- 게시판 타입 --%>
<%-- 
<c:set var="boardType">
<c:choose>
	<c:when test="${not empty searchVO.boardType}">
		<c:out value="${searchVO.boardType}"></c:out>
	</c:when>
	<c:otherwise>
		NORMAL
	</c:otherwise>
</c:choose>
</c:set>
 --%>
<%-- 기본 URL --%>
<c:url var="_BASE_PARAM" value="">
	<%-- <c:param name="boardType" value="${boardType}" /> --%>
  	<c:if test="${not empty searchVO.searchCondition}"><c:param name="searchCondition" value="${searchVO.searchCondition}" /></c:if>
  	<c:if test="${not empty searchVO.searchKeyword}"><c:param name="searchKeyword" value="${searchVO.searchKeyword}" /></c:if>
</c:url>


<c:choose>
	<c:when test="${not empty searchVO.boardId}">
		<c:set var="actionUrl" value="/board/update.do"/>
	</c:when>
	<c:otherwise>
		<c:set var="actionUrl" value="/board/insert.do"/>
	</c:otherwise>
</c:choose>

<div class="container">
	<div id="contents">
		<form action="${actionUrl}" method="post" id="frm" name="frm" onsubmit="return regist()" enctype="multipart/form-data"> 
			<input type="hidden" name="boardId" value="${result.boardId}"/>
			<%-- 첨부파일 삭제 때문에 returnUrl존재 --%>
			<input type="hidden" name="returnUrl" value="/board/boardRegist.do"/>
			
			<%-- 게시판 타입 --%>
			<%-- <input type="hidden" name="boardType" value="${searchVO.boardType}"/> --%>
			
			<table class="chart2">
		        <caption>게시글 작성</caption>
		        <colgroup>
		            <col style="width:120px" />
		            <col />
		        </colgroup>
		        <tbody>
		            <tr>
		                <th scope="row">제목</th>
		                <td>
		                    <input type="text" id="boardSj" name="boardSj" title="제목입력" class="q3" value="<c:out value="${result.boardSj}"/>"/>
		                </td>
		            </tr>
		            <tr>
		                <th scope="row">공지여부</th>
		                <td>
		                    <label for="noticeAtY">예 : </label> 
							<input type="radio" id="noticeAtY" value="Y" name="noticeAt" <c:if test="${result.noticeAt eq 'Y'}">checked="checked"</c:if>/>
							&nbsp;&nbsp;&nbsp;
							<label for="noticeAtN">아니오 : </label> 
							<input type="radio" id="noticeAtN" value="N" name="noticeAt" <c:if test="${result.noticeAt ne 'Y'}">checked="checked"</c:if>/>
		                </td>
		            </tr>
		            <tr>
		                <th scope="row">비공개여부</th>
		                <td>
		                    <label for="othbcAtY">예 : </label> 
							<input type="radio" id="othbcAtY" value="Y" name="othbcAt" <c:if test="${result.othbcAt eq 'Y'}">checked="checked"</c:if>/>
							&nbsp;&nbsp;&nbsp;
							<label for="othbcAtN">아니오 : </label> 
							<input type="radio" id="othbcAtN" value="N" name="othbcAt" <c:if test="${result.othbcAt ne 'Y'}">checked="checked"</c:if>/>
		                </td>
		            </tr>
		            <tr>
		                <th scope="row">작성자ID</th>
		                <td>
		                    <c:out value="${USER_INFO.id}"/>
		                </td>
		            </tr>
		            <tr>
		                <th scope="row">내용</th>
		                <td>
		                    <textarea id="boardCn" name="boardCn" rows="15" title="내용입력"><c:out value="${result.boardCn}"/></textarea>
		                </td>
		            </tr>
		            
		            <c:if test="${not empty result.atchFileId}">
			            <tr>
			                <th scope="row">기존<br/>첨부파일목록</th>
			                <td>
			                	<c:import url="/cmm/fms/selectFileInfoForUpdate.do" charEncoding="utf-8">
				                    <c:param name="param_atchFileId" value="${result.atchFileId}" />
				                </c:import>    
			                </td>
			            </tr>
		            </c:if>
		            <tr>
		                <th scope="row">파일첨부</th>
		                <td>
		                    <input type="file" name="file_1"/>
		                    <br/>
		                    <input type="file" name="file_2"/>
		                </td>
		            </tr>
		            
		        </tbody>
		    </table>
			<div class="btn-cont ar">
			    <c:choose>
					<c:when test="${not empty searchVO.boardId}">
						<c:url var="uptUrl" value="/board/update.do">
							<c:param name="boardId" value="${result.boardId}"/>
						</c:url>
						<a href="${uptUrl}" id="btn-reg" class="btn">수정</a>
						
						<c:url var="delUrl" value="/board/delete.do">
							<c:param name="boardId" value="${result.boardId}"/>
						</c:url>
			    		<a href="${delUrl}" id="btn-del" class="btn"><i class="ico-del"></i> 삭제</a>
					</c:when>
					<c:otherwise>
						<a href="#none" id="btn-reg" class="btn spot">등록</a>
					</c:otherwise>
				</c:choose>
				<c:url var="listUrl" value="/board/selectList.do${_BASE_PARAM}"/>
			    <a href="${listUrl}" class="btn">취소</a>
			</div>
		</form>
	</div>
</div>
<script>
$(document).ready(function(){
	//게시 글 등록
	$("#btn-reg").click(function(){
		$("#frm").submit();
		return false;
	});
	
	//게시 글 삭제
	$("#btn-del").click(function(){
		if(!confirm("삭제하시겠습니까?")){
			return false;
		}
	});
});

function regist(){
	if(!$("#boardSj").val()){
		alert("제목을 입력해주세요.");
		$("#boardSj").focus();
		return false;
	}
}
</script>

<script src="https://cdn.tiny.cloud/1/xg9uuf6dha0abv164r3ngodmfu0p9vzo66mbdo8vtuiooqr9/tinymce/6/tinymce.min.js" referrerpolicy="origin"></script>
<script>
$(function(){
    var plugins = [
        "advlist", "autolink", "lists", "link", "image", "charmap", "print", "preview", "anchor",
        "searchreplace", "visualblocks", "code", "fullscreen", "insertdatetime", "media", "table",
        "paste", "code", "help", "wordcount", "save"
    ];
    var edit_toolbar = 'formatselect fontselect fontsizeselect |'
               + ' forecolor backcolor |'
               + ' bold italic underline strikethrough |'
               + ' alignjustify alignleft aligncenter alignright |'
               + ' bullist numlist |'
               + ' table tabledelete |'
               + ' link image';

    tinymce.init({
    language: "ko_KR", //한글판으로 변경
        selector: '#boardCn',
        height: 500,
        menubar: false,
        plugins: plugins,
        toolbar: edit_toolbar,
        
        /*** image upload ***/
        image_title: true,
        /* enable automatic uploads of images represented by blob or data URIs*/
        automatic_uploads: true,
        /*
            URL of our upload handler (for more details check: https://www.tiny.cloud/docs/configure/file-image-upload/#images_upload_url)
            images_upload_url: 'postAcceptor.php',
            here we add custom filepicker only to Image dialog
        */
        file_picker_types: 'image',
        /* and here's our custom image picker*/
        file_picker_callback: function (cb, value, meta) {
            var input = document.createElement('input');
            input.setAttribute('type', 'file');
            input.setAttribute('accept', 'image/*');

            /*
            Note: In modern browsers input[type="file"] is functional without
            even adding it to the DOM, but that might not be the case in some older
            or quirky browsers like IE, so you might want to add it to the DOM
            just in case, and visually hide it. And do not forget do remove it
            once you do not need it anymore.
            */
            input.onchange = function () {
                var file = this.files[0];

                var reader = new FileReader();
                reader.onload = function () {
                    /*
                    Note: Now we need to register the blob in TinyMCEs image blob
                    registry. In the next release this part hopefully won't be
                    necessary, as we are looking to handle it internally.
                    */
                    var id = 'blobid' + (new Date()).getTime();
                    var blobCache =  tinymce.activeEditor.editorUpload.blobCache;
                    var base64 = reader.result.split(',')[1];
                    var blobInfo = blobCache.create(id, file, base64);
                    blobCache.add(blobInfo);

                    /* call the callback and populate the Title field with the file name */
                    cb(blobInfo.blobUri(), { title: file.name });
                };
                reader.readAsDataURL(file);
            };
            input.click();
        },
        /*** image upload ***/
        
        content_style: 'body { font-family:Helvetica,Arial,sans-serif; font-size:14px }'
    });
});
</script>
 
 
<c:import url="/template/footer.do" charEncoding="utf-8"/>
