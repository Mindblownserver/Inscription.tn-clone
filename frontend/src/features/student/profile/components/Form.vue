<template>
    <Fieldset legend="Info sur L'etudiant" :toggleable="true">
        <div class="space"></div>
        <MyInputText label="Cin" :disabled="true" v-model:value="formData.cin"/>
        <MyInputText label="Nom" v-model:value="formData.nomFrEtu"/>
        <MyInputText label="Prenom" v-model:value="formData.prenomFrEtu"/>
        <MyInputText label="الاسم"  v-model:value="formData.prenomArEtu"/> 
        <MyInputText label="اللقب" v-model:value="formData.nomArEtu"/> 
        <MyInputText label="Date de naissance" v-model:value="formData.dateNaiss"/>
        <Button label="Choose File" @click="chooseFile" text/>
        <input type="file" name="inputFile" id="inputFile" style="display: none;" @change="(e)=>{previewImg(e);handleFileChange(e)}">
        <img src="" id="previewImg" alt="choisir une image">
    </Fieldset>
    <div class="space"></div>
    <Fieldset legend="Info sur le père de l'etudiant" :toggleable="true">
        <div class="space"></div>
        <MyInputText label="Nom" v-model:value="formData.nomPere"/> 
        <MyInputText label="Prenom" v-model:value="formData.prenomPere"/>
        <MyInputText label="Profession" v-model:value="formData.professionPere"/>
    </Fieldset>
    <div class="space"></div>
    <Fieldset legend="Info sur la mère de l'etudiant" :toggleable="true">
        <div class="space"></div>
        <MyInputText label="Nom" v-model:value="formData.nomMere"/> 
        <MyInputText label="Prenom" v-model:value="formData.prenomMere"/>
        <MyInputText label="Profession" v-model:value="formData.professionMere"/>
    </Fieldset>
    <div class="space"></div>
    <div class="btnLayout">
        <Button label="Enregistrer" severity="success" raised @click="saveChanges"/>
    </div>
</template>

<script setup lang="ts">
import MyInputText from './LblInputText.vue';
import Fieldset from 'primevue/fieldset';
import Button from 'primevue/button';
import { ref } from 'vue';
import { Student } from '../../utilities/interfaces';

const selectedFile = ref<File | null>(null);
const blob = ref<Blob | null>(null);

let props=defineProps<{formData: Student}>()

const emit = defineEmits(['update:formData','update:Student']);

const saveChanges = ()=>{
    if (!selectedFile.value) {
      return;
    }

    // The File object is already a Blob
    blob.value = selectedFile.value;
    emit("update:Student",blob.value)
}

const updateValue = () => {
  emit('update:formData',props.formData);
};
const chooseFile = function(){
    document.getElementById("inputFile")?.click()
}


const previewImg=function(event: any){
    const file = event.target.files[0];
  if (file) {
    const reader = new FileReader();
    reader.onload = function(e: any) {
      const img:any = document.getElementById('previewImg');
      if(img){
          img.src = e.target.result;
          img.style.display = 'block'; // Show the image
      }
    };
    reader.readAsDataURL(file);
  }
}

const handleFileChange = (event: any) => {
  const input = event.target as HTMLInputElement;
  if (input.files && input.files[0]) {
    selectedFile.value = input.files[0];
  }
};

</script>

<style scoped>
.space{
    height: 30px;
}
.btnLayout{
    display: flex;
    justify-content: end;
}
</style>