const loading_selector = $(".overall-situation-loading");
const notice_type = {
    "info": {
        "type": "info",
        "icon": "info"
    },
    "danger": {
        "type": "danger",
        "icon": "error"
    },
    "success": {
        "type": "success",
        "icon": "check_circle"
    },
    "warning": {
        "type": "warning",
        "icon": "warning"
    },
    "rose": {
        "type": "rose",
        "icon": "info"
    },
    "primary": {
        "type": "primary",
        "icon": "info"
    }
};
const request_method = {
    "get": "GET",
    "post": "POST",
    "put": "PUT",
    "delete": "DELETE"
};

const login_url = "http://172.23.41.184:8888/oauth/token";

/**
 * 封装ajax请求
 * @param url 请求路径
 * @param data 请求数据
 * @param type 请求类型
 * @param callback 回调函数
 */
function requestJson(url, data, type, callback) {
    loading_selector.modal("show");
    $.ajax({
        url: url,
        contentType: "application/json; charset=utf-8",
        data: JSON.stringify(data),
        type: type,
        cache: false,
        processData: false,
        dataType: 'json',
        beforeSend: function (xhr) {
            //请求前
            console.log("before...");
        },
        success: function (data) {
            //请求成功
            console.log("success...");
            callback(data);
        },
        error: function (xhr, textStatus, err) {
            //请求失败
            console.log("fail...", err);
        },
        complete: function (xhr, status) {
            //请求完成
            console.log("complete...");
        }
    });
    loading_selector.modal("hide");
}

function requestFormData(url, data, type, callback) {
    loading_selector.modal("show");
    $.ajax({
        url: login_url + "?username=" + data.get('username') + "&password=" + data.get('password') + "&grant_type=" + data.get('grant_type'),
        type: type,
        cache: false,
        contentType: false,
        processData: false,
        dataType: 'json',
        beforeSend: function (xhr) {
            console.log("before...");
            xhr.setRequestHeader('Authorization', 'Basic Y2xpZW50LUE6a2FybA==');
        },
        success: function (data) {
            console.log("success...");
            callback(data)
        },
        error: function (xhr, textStatus, err) {
            console.log("fail...", err);
        },
        complete: function (xhr, status) {
            console.log("complete...", status);
        }
    });
    loading_selector.modal("hide");
}

/**
 * 封装提示方法
 * @param notice_type
 * @param msg
 */
function notice(notice_type, msg) {
    $.notify({
        icon: notice_type['icon'],
        message: msg
    }, {
        type: notice_type['type'],
        timer: 1e1,
        placement: {
            from: 'top',
            align: 'center'
        }
    })
}