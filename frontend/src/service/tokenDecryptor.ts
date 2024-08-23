export function tokenToId(accessToken: string | null): string|null{
    if(accessToken){
        const arrayToken = accessToken.split('.');
        try{
            const tokenPayload = JSON.parse(atob(arrayToken[1]));
            return tokenPayload.upn;

        }catch(error){
            console.error("Token To Id: ", error)
        }
    }
    return null;
}