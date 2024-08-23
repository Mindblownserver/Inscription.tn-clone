interface UserInfo{
    id: String,
    name: string
}
interface RefreshTokenError extends Error{
}
function RefreshTokenErrorFct(msg: string){
    const error = new Error(msg) as RefreshTokenError;
    error.name = "RefreshTokenError";
    return error;
}
function isRefreshTokenError(error: Error): boolean{
    return error.name==="RefreshTokenError";
}


export {UserInfo,RefreshTokenError, RefreshTokenErrorFct, isRefreshTokenError};  