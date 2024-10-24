import {ElMessage, ElMessageBox} from "element-plus";

export function messageTip(message, type) {
    ElMessage({
        showClose: true,
        center: true,
        message: message,
        type: type,
    })
}

export function messageConfirm(message) {
    return ElMessageBox.confirm(
        message,
        'Warning',
        {
            confirmButtonText: 'OK',
            cancelButtonText: 'Cancel',
            type: 'warning',
        }
    )
}

export function getTokenName() {
    return "dlyk_token";
}

export function removeTokens() {
    window.localStorage.removeItem(getTokenName());
    window.sessionStorage.removeItem(getTokenName());
}