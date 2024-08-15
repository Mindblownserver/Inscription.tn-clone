export function tokenToId(accessToken: string | null): string{
    if(accessToken){
        const arrayToken = accessToken.split('.');
        const tokenPayload = JSON.parse(atob(arrayToken[1]));
        return tokenPayload.upn;
    }
    return "";
}