<template>
    <Fieldset legend="Info sur L'etudiant" :toggleable="true">
        <div class="space"></div>
        <MyInputText /> 
        <MyInputText />
        <MyInputText />
        <MyInputText /> 
        <Button label="Choose File" @click="chooseFile"/>
        <input type="file" name="inputFile" id="inputFile" style="display: none;" @change="previewImg($event)">
        <img src="" id="previewImg" alt="choisir une image">
    </Fieldset>
    <div class="space"></div>
    <Fieldset legend="Info sur le père de l'etudiant" :toggleable="true">
        <div class="space"></div>
        <MyInputText /> 
        <MyInputText />
        <MyInputText />
    </Fieldset>
    <div class="space"></div>
    <Fieldset legend="Info sur la mère de l'etudiant" :toggleable="true">
        <div class="space"></div>
        <MyInputText /> 
        <MyInputText />
        <MyInputText />
    </Fieldset>
</template>

<script setup lang="ts">
import MyInputText from './LblInputText.vue';
import Fieldset from 'primevue/fieldset';
import Button from 'primevue/button';

const chooseFile = function(){
    document.getElementById("inputFile")?.click()
}

const previewImg=function(event){
    const file = event.target.files[0];
  if (file) {
    const reader = new FileReader();
    reader.onload = function(e) {
      const img = document.getElementById('previewImg');
      img.src = e.target.result;
      img.style.display = 'block'; // Show the image
    };
    reader.readAsDataURL(file);
  }
}
</script>

<style scoped>
.space{
    height: 30px;
}
</style>