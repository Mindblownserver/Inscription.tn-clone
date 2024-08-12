<template>
    
    <div class="container">
        
        <div class="carteElements">
            <img src="../../../assets/images/pixelcut-export.png" alt="">
            <h3 class="title">Carte Etudiant 2024 - 2025</h3>
            <img class="profile" src="../../../assets/images/profileBlob.png" alt="">
            <p class="nameAr">فولان فولاني</p>
            <p class="nameFr">Foulan l Foulani</p>
            <p class="dateFr">Né(e) le 28/03/1987 </p>
            <p class="filliere1">السنة الأولى في </p>
            <p class="filliere2">معلومات الترخيص</p>
            
            <img class="Qr" src="../../../assets/images/QrCode.png" alt="">
        </div>
    </div>
</template>
<script setup lang="ts">

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