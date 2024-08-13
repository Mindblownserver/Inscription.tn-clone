<template>
    
    <div class="container">
        
        <div class="carteElements">
            <img src="../../../assets/images/pixelcut-export.png" alt="">
            <h3 class="title">Carte Etudiant 2024 - 2025</h3>
            <img class="profile" id="profile" src="../../../assets/images/profileBlob.png" alt="">
            <p id="nomAr" class="nameAr">فولان فولاني</p>
            <p id="nomFr" class="nameFr">Foulan l Foulani</p>
            <p id="date" class="dateFr">Né(e) le 28/03/1987</p>
            <p id="niveauFill" class="filliere1">السنة الأولى في </p>
            <p id="fillLib" class="filliere2">معلومات الترخيص</p>
            
            <img class="Qr" src="../../../assets/images/QrCode.png" alt="">
        </div>
    </div>
</template>
<script setup lang="ts">
import { Etablissement, Inscription,Student, University } from '../utilities/interfaces';

// Convert Uint8Array to Blob
function uint8ArrayToBlob(uint8Array: Uint8Array, mimeType: string): Blob {
    return new Blob([uint8Array], { type: mimeType });
}

// Create an object URL for the Blob
function createImageUrlFromBlob(blob: Blob): string {
    return URL.createObjectURL(blob);
}

function displayImageFromUint8Array(uint8Array: Uint8Array, mimeType: string|null): void {
    // Convert Uint8Array to Blob
    const type = mimeType || 'application/octet-stream';

    const blob = uint8ArrayToBlob(uint8Array, type);
    
    // Create an object URL for the Blob
    const imageUrl = createImageUrlFromBlob(blob);
    
    // Get the <img> element and set its src attribute
    const imgElement = document.getElementById("profile") as HTMLImageElement;
    if (imgElement) {
        imgElement.src = imageUrl;
    } else {
        console.error('Image element not found.');
    }
    
    
}

const loadCardInfo = function(inscriptionData:Inscription){
    console.log(inscriptionData)
    let nomFr: HTMLElement = document.getElementById('nomFr') as HTMLElement
    let nomAr: HTMLElement = document.getElementById('nomAr') as HTMLElement
    let date: HTMLElement = document.getElementById('date') as HTMLElement
    let niveauFill: HTMLElement = document.getElementById('niveauFill') as HTMLElement
    let fillLib:HTMLElement = document.getElementById('fillLib') as HTMLElement
    
    let uni:University = inscriptionData.fill_etab.etab.uni;
    let etab:Etablissement = inscriptionData.fill_etab.etab;
    let etu:Student = inscriptionData.etu;
    let birthDate:Date = new Date(etu.dateNaiss);
    if(nomFr && nomAr && date && niveauFill && fillLib){
        nomFr.innerText=etu.prenomFrEtu + " "+etu.nomFrEtu
        nomAr.innerText= etu.prenomArEtu +" "+etu.nomArEtu
        date.innerText = `Né(e) le ${birthDate.getDate()}/${birthDate.getMonth()+1}/${birthDate.getFullYear()}`;
        
    }

}

defineExpose({
    loadCardInfo
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
    top: 30px;
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
.nameAr, .nameFr, .dateFr{
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

</style>