<template>
    <div class="profileContainer">
        <div class="left">
            <h2>Additional information</h2>
            <Form v-model:formData="formData" @update:Student="updateCarte"/>
        </div>
        
        <Divider class="divider" layout="vertical" />
        
        <div class="right">
            <div>
                <h2>Preview</h2>
                <CarteEtudiantVerif ref="carte"/>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import CarteEtudiantVerif from '../../components/CarteEtudiantVerif.vue';
import Divider from 'primevue/divider';
import Form from "../components/Form.vue"
import { onMounted, ref } from 'vue';
import { useStore } from 'vuex';
import { Student } from '../../utilities/interfaces';
import { tokenToId } from '@/service/tokenDecryptor';

interface carteEtu{
    loadPreviewCardInfo: (data: Student)=>void
}

const store = useStore();
let formData=ref<Student>({
    cin: "",
    nomFrEtu: "",
    prenomFrEtu: "",
    nomArEtu: "",
    prenomArEtu: "",
    dateNaiss: "",
    nomPere: "",
    prenomPere: "",
    professionPere: "",
    nomMere: "",
    prenomMere: "",
    professionMere: "",
    photoEtu: "",

})

let carte=ref<carteEtu>();

const updateCarte = (blob:Blob)=>{
    console.log(blob)
    if(blob != null){
        blobToBase64(blob).then(base64Image=>{
            store.commit("studentModule/setStudentImage", base64Image)
            formData.value.photoEtu=base64Image;
            carte.value?.loadPreviewCardInfo(formData.value);
            
        })
    }else{
        carte.value?.loadPreviewCardInfo(formData.value);
    }
    
}

function blobToBase64(blob: Blob): Promise<string> {
  return new Promise((resolve, reject) => {
    const reader = new FileReader();

    reader.onloadend = () => {
      if (reader.result) {
        // reader.result is a Data URL (base64 encoded)
        const base64String = (reader.result as string).split(',')[1];
        resolve(base64String);
      } else {
        reject('Failed to convert blob to Base64');
      }
    };

    reader.onerror = (error) => {
      reject(`FileReader error: ${error}`);
    };

    // Read the blob as a Data URL
    reader.readAsDataURL(blob);
  });
}


onMounted(()=>{
    store.dispatch("studentModule/getStudentByCin", tokenToId(localStorage.getItem("accessToken"))).then(()=>{
        console.log(store.state.studentModule.student);
        formData.value = store.state.studentModule.student;
        carte.value?.loadPreviewCardInfo(formData.value);
    })

})
</script>

<style scoped>
.profileContainer{
    display: grid;
    grid-template-columns: 112px 1fr 50px 1fr 100px;
}

.right{
    grid-column: 4/5;
    grid-row: 1/2;
    justify-self: end;
}
.right>div{
    position: sticky;
    position: -webkit-sticky;
    top: 0;
}

.left{
    grid-column: 2/3;
    grid-row: 1/2;
    justify-self: start;
}
.divider{
    grid-column: 3/4;
    padding: 0;
    border-radius: 50%;
    
}
.divider::before{
    border-left: 2px solid var(--p-divider-border-color);
}
.right>h2{
    justify-self: start;
}

</style>