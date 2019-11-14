/**
 * 提交回复
 */
function post() {
    var questionId = $("#question_id").val();
    var content = $("#comment_content").val();
    comment2target(questionId, 1, content);
}

/**
 * 展开二级评论
 */
function collapseComments(e) {
    var id = e.getAttribute("data-id");
    console.log(id);
    var comments = $("#comment-" + id);
    // 获取二级评论的展开状态
    var collapse = e.getAttribute("data-collapse");
    if (collapse) {
        // 判断如果已经展开，则点击时候删除in
        comments.removeClass("in");
        e.removeAttribute("data-collapse");
        e.classList.remove("active");
    } else {
        var subCommentContainer = $("#comment-" + id);
        if (subCommentContainer.children().length != 1) {
            // 通过修改class为Collapse in 来展示二级评论
            comments.addClass("in");
            // 标记二级评论展开状态
            e.setAttribute("data-collapse", "in");
            e.classList.add("active");
        } else {
            $.getJSON("/comment/" + id, function (data) {
                console.log(data);
                var items = [];
                $.each(data.data.reverse(), function (index, comment) {
                    console.log(comment);
                    var avatarElement = $("<img/>", {
                        "class": "media-object img-rounded",
                        "src": comment.user.avatarUrl
                    });
                    var mediaLeftElement = $("<div/>", {
                        "class": "media-left"
                    });
                    mediaLeftElement.append(avatarElement);
                    var mediaBodyElement = $("<div/>", {
                        "class": "media-body"
                    }).append($("<h5/>", {
                        "class": "media-heading",
                        "html": comment.user.name
                    })).append($("<div/>", {
                        "html": comment.content
                    })).append($("<div/>", {
                        "class": "menu"
                    }).append($("<span/>", {
                        "class": "pull-right",
                        "html": moment(comment.gmtCreate).format('YYYY-MM-DD')
                    })));
                    var mediaElement = $("<div/>", {
                        "class": "media"
                    }).append(mediaLeftElement)
                        .append(mediaBodyElement);
                    var commentElement = $("<div/>", {
                        "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 comments",
                    });
                    commentElement.append(mediaElement);
                    subCommentContainer.prepend(commentElement);
                });
                // $("<div/>", {
                //         "class": "col-lg-12 col-md-12 col-sm-12 col-xs-12 collapse sub-comments",
                //         "id": "comment-" + id,
                //         html: items.join("")
                //     }
                // ).appendTo(commentBody);

                // 通过修改class为Collapse in 来展示二级评论
                comments.addClass("in");
                // 标记二级评论展开状态
                e.setAttribute("data-collapse", "in");
                e.classList.add("active");
            });
        }

    }
}

function comment2target(targetId, type, content) {
    if (!content) {
        alert("不能回复空内容哦哦哦~");
        return;
    }
    $.ajax({
        type: 'POST',
        url: "/comment",
        contentType: "application/json",
        data: JSON.stringify({
            "parentId": targetId,
            "content": content,
            "type": type
        }),
        success: function (response) {
            if (response.code == 200) {
                window.location.reload();
            } else {
                if (response.code == 2003) {
                    var isAccepted = confirm(response.message);
                    if (isAccepted) {
                        window.open("https://github.com/login/oauth/authorize?client_id=3d4a01bfd5d5fade33b7&redirect_uri=http://localhost:8181/callback&&scope=user&state=1");
                        window.localStorage.setItem("closable", true);
                    }
                } else {
                    alert(response.message());
                }
            }
            console.log(response);
        },
        dataType: "json"
    });
}

function comment(e) {
    var commentId = e.getAttribute("data-id");
    var content = $("#input-" + commentId).val();
    comment2target(commentId, 2, content)
}