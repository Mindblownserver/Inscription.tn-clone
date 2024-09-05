function createImageUrl(base64Image: String, mimeType: string): string {
    return `data:${mimeType};base64,${base64Image}`;
}

function displayImageFromUint8Array(base64ProfileImage: string, base64EtabImage:string=" ",base64UniImage:string=" "): void {
    const mimeType:string="application/octet-stream"

    const profileUrl = createImageUrl(base64ProfileImage, mimeType);
    const etabUrl = createImageUrl(base64EtabImage,mimeType)
    const uniUrl = createImageUrl(base64UniImage,mimeType)

    const profileElement = document.getElementById("profile") as HTMLImageElement;
    const etabElement = document.getElementById("etabImg") as HTMLImageElement;
    const uniElement = document.getElementById("uniImg") as HTMLImageElement;

    if (profileElement) {
        profileElement.src = profileUrl;

    } 
    if(etabElement){
        etabElement.src=etabUrl;
        console.debug("Seen etab")
    }
    if(uniElement){
        uniElement.src=uniUrl;
    }   
}

function getImageFromUint8Array(base64ProfileImage: string, isVoid:boolean=true, mimeType:string="application/octet-stream", base64EtabImage:string=" ",base64UniImage:string=" "): string {

    return createImageUrl(base64ProfileImage, mimeType);
}
export {displayImageFromUint8Array, getImageFromUint8Array}