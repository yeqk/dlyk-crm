import {ElMessage} from "element-plus";

export function messageTip(message, type) {
    ElMessage({
        showClose: true,
        center: true,
        message: message,
        type: type,
    })
}

export function getTokenName() {
    return "dlyk_token";
}