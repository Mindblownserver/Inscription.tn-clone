<template>
    
    <div class="container">
        
        <div class="carteElements">
            <img src="../../../assets/images/pixelcut-export.png" alt="">
            <h3 class="title">Carte Etudiant 202x - 202x</h3>
            <img class="profile" id="profile" src="../../../assets/images/profileBlob.png" alt="">
            <p id="nomAr" class="nameAr">فولان فولاني</p>
            <p id="nomFr" class="nameFr">Foulan l Foulani</p>
            <p id="date" class="dateFr">Né(e) le 28/03/1987</p>
            <p id="niveauFill" class="filliere1">السنة ... في </p>
            <p id="fillLib" class="filliere2">....</p>
            <p id="uniTitleFr" >xxx</p>
            <p id="uniTitleAr" >xxx</p>
            <img id="etabImg"  src="" alt="">
            <img id="uniImg"  src="" alt="">
            <img class="Qr" src="../../../assets/images/QrCode.png" alt="">
        </div>
    </div>
</template>
<script setup lang="ts">
import { Etablissement, Filliere, Inscription,Student, University } from '../utilities/interfaces';

// Create an object URL for the Blob
function createImageUrl(base64Image: String, mimeType: string): string {
    return `data:${mimeType};base64,${base64Image}`;
}

function displayImageFromUint8Array(base64ProfileImage: string, mimeType: string|null, base64EtabImage:string=" ",base64UniImage:string=" "): void {
    const type = mimeType || 'application/octet-stream';

    const profileUrl = createImageUrl(base64ProfileImage, type);
    const etabUrl = createImageUrl(base64EtabImage,type)
    const uniUrl = createImageUrl(base64UniImage,type)

    const profileElement = document.getElementById("profile") as HTMLImageElement;
    const etabElement = document.getElementById("etabImg") as HTMLImageElement;
    const uniElement = document.getElementById("uniImg") as HTMLImageElement;

    if (profileElement && etabElement && uniElement) {
        profileElement.src = profileUrl;

    } 
    if(etabElement){
        etabElement.src=etabUrl;
    }
    if(uniElement){
        uniElement.src=uniUrl;
    }
    
    
}

const loadCardInfo = function(inscriptionData:Inscription){
    let nomFr: HTMLElement = document.getElementById('nomFr') as HTMLElement
    let nomAr: HTMLElement = document.getElementById('nomAr') as HTMLElement
    let date: HTMLElement = document.getElementById('date') as HTMLElement
    let niveauFill: HTMLElement = document.getElementById('niveauFill') as HTMLElement
    let fillLib:HTMLElement = document.getElementById('fillLib') as HTMLElement
    let uniTitleFr: HTMLElement = document.getElementById('uniTitleFr') as HTMLElement
    let uniTitleAr:HTMLElement = document.getElementById('uniTitleAr') as HTMLElement

    let uni:University = inscriptionData.fill_etab.etab.uni;
    let etab:Etablissement = inscriptionData.fill_etab.etab;
    let etu:Student = inscriptionData.etu;
    let fill:Filliere = inscriptionData.fill_etab.fill;
    let birthDate:Date = new Date(etu.dateNaiss);
    if(nomFr && nomAr && date && niveauFill && fillLib){
        nomFr.innerText=etu.prenomFrEtu + " "+etu.nomFrEtu
        nomAr.innerText= etu.prenomArEtu +" "+etu.nomArEtu
        date.innerText = `Né(e) le ${birthDate.getDate()}/${birthDate.getMonth()+1}/${birthDate.getFullYear()}`;
        niveauFill.innerText= fill.nomArFill.split(" ",3). join(" ");
        fillLib.innerText=fill.nomArFill.split(" ").slice(3).join(" ");
        uniTitleAr.innerText = etab.nomArEtab;
        uniTitleFr.innerText = etab.nomFrEtab;
        displayImageFromUint8Array(etu.photoEtu,etab.photoEtab, uni.photoUni, 'application/octet-stream');
    }

}

const loadPreviewCardInfo = function(etu:Student){
    let nomFr: HTMLElement = document.getElementById('nomFr') as HTMLElement
    let nomAr: HTMLElement = document.getElementById('nomAr') as HTMLElement
    let date: HTMLElement = document.getElementById('date') as HTMLElement
    let niveauFill: HTMLElement = document.getElementById('niveauFill') as HTMLElement
    let fillLib:HTMLElement = document.getElementById('fillLib') as HTMLElement

    let birthDate:Date = new Date(etu.dateNaiss);
    if(nomFr && nomAr && date && niveauFill && fillLib){
        nomFr.innerText=etu.prenomFrEtu + " "+etu.nomFrEtu
        nomAr.innerText= etu.prenomArEtu +" "+etu.nomArEtu
        date.innerText = `Né(e) le ${birthDate.getDate()}/${birthDate.getMonth()+1}/${birthDate.getFullYear()}`;
        displayImageFromUint8Array(etu.photoEtu, 'application/octet-stream');
    }

}


defineExpose({
    loadCardInfo,
    loadPreviewCardInfo
})
</script>

<style scoped>

img{
    width: 500px;
}
.container{
    display: flex;
    flex-direction: column;
    align-items: start;
    margin-left: 10px;
}


.carteElements{
    position: relative;
}
.title{
    text-transform: uppercase;
    position: absolute;
    top: 45px;
    left: 110px;
}
.profile{
    width: 130px;
    position: absolute;
    left: 10px;
    top: calc(30px + 60px);
}
.Qr{
    width: 150px;
    position: absolute;
    top: calc(30px + 60px);
    right: 10px;
}
.filliere1{
    position: absolute;
    bottom: 80px;
    right: calc(10px + 155px);
    font-size: 16px;
}
.filliere2{
    position: absolute;
    bottom: 60px;
    right: calc(10px + 165px);
    font-size: 16px;
}
.nameAr, .nameFr, .dateFr, #uniTitleAr, #uniTitleFr, #etabImg, #uniImg{
    position: absolute;
}
.nameAr{
    text-align: end;
    top: 80px;
    right: 165px;
}

.nameFr{
    text-align: start;
    top: 105px;
    left: 145px;
}
.dateFr{
    bottom: 120px;
    left: 145px;
}

#uniTitleFr{
    text-transform: uppercase;
    top: 0px;
    font-weight: bold;  
    font-size: 10px;  
    left: 110px;
    width: 270px;
}

#uniTitleAr{
    text-transform: uppercase;
    top: 25px;
    font-weight: bold;  
    font-size: 10px;  
    right: 120px;
    text-align: end;
    width: 270px;
}

#etabImg{
    left: 10px;
    top: 0;
    width: 90px;
}

#uniImg{
    right: 10px;
    top: 0;
    width: 90px;
}
</style>