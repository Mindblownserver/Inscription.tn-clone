import { ToastServiceMethods } from "primevue/toastservice";

const showSuccess = (toast: ToastServiceMethods,title: string, message: string) => {
    toast.add({ severity: 'success', summary: title, detail: message, life: 3000 });
};
const showError = (toast: ToastServiceMethods, title: string, message: string) => {
    toast.add({ severity: 'error', summary: title, detail: message, life: 3000 });
};

export {showError, showSuccess}