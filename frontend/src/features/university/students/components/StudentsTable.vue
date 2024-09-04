<template>
    <!-- :globalFilterFields="['name', 'country.name', 'representative.name', 'status']" -->
    <DataTable 
        v-model:filters="filters" :value="getInscriptions" 
        paginator :rows="10" filterDisplay="row"  size="large" 
        :loading="getLoading">
        <template #header>
            <div class="headerContainer">
                <Select v-model="au" :options="['2021-2022','2022-2023','2023-2024','2024-2025']" 
                placeholder="Select a university year" class="full-width md-width-56" showClear checkmark 
                @change="handleAuChange"/>
            </div>
        </template>
        <template #empty> No Inscriptions found. </template>
        <template #loading> Loading Inscriptions data. Please wait. </template>
        <Column header="Etudiant" style="min-width: 20rem" filterField="etu.cin">
            <template #body="etudiant">
                <div class="etuContainer">
                    <img :src="getImageFromUint8Array(etudiant.data.etu.photoEtu)" alt="image de l'etudiant" style="width: 80px;">
                    <div class="etuProps">
                        <p>{{etudiant.data.etu.prenomFrEtu + " " + etudiant.data.etu.nomFrEtu}}</p>
                        <span>{{etudiant.data.etu.cin}}</span>
                    </div>
                </div>
            </template>
            <template #filter="{ filterModel, filterCallback }">
                <InputText v-model="filterModel.value" type="text" @input="filterCallback()" placeholder="Search by cin" />
            </template>
        </Column>
        <Column v-for="(col, index) in columns" :key="index" :field="col.field" :header="col.header" sortable
        :style="'min-width: '+col.width">
            <template #filter="{ filterModel, filterCallback }">
                <InputText v-model="filterModel.value" type="text" @input="filterCallback()" placeholder="Search by name" />
            </template>
        </Column>
    </DataTable>
</template>

<script setup lang="ts">

import { FilterMatchMode } from '@primevue/core/api';
import DataTable from 'primevue/datatable';
import Column from 'primevue/column';
import { computed, onMounted, ref } from 'vue';
import { useStore } from 'vuex';
import InputText from "primevue/inputtext"
import Select from 'primevue/select';
import { tokenToId } from '@/service/tokenDecryptor';
import { getImageFromUint8Array } from '@/utilities/base64ImageConversion';

const au = ref("2024-2025")

const columns = [
    {field: 'etu.dateNaiss', header: 'Date Naissance', width: "10rem"},
    {field: 'fill_etab.fill.nomFrFill', header: 'Filliere', width: "25rem"},
    {field: 'fill_etab.prixTotal', header: 'Prix', width: "10px"},
    {field: 'paid', header: 'Paid', width: "5px"},
]

const filters = ref({
    'etu.cin': { value: null, matchMode: FilterMatchMode.STARTS_WITH },
    'etu.dateNaiss': { value: null, matchMode: FilterMatchMode.STARTS_WITH },
    'fill_etab.fill.nomFrFill': { value: null, matchMode: FilterMatchMode.STARTS_WITH },
    'fill_etab.prixTotal': { value: null, matchMode: FilterMatchMode.STARTS_WITH },
    'paid': { value: null, matchMode: FilterMatchMode.EQUALS },
})

const store = useStore();


const getLoading = computed(()=>store.state.uniModule.loading);
const getInscriptions = computed(()=>store.state.uniModule.inscriptions);

const handleAuChange = ()=>{
    console.log("Changed")
}
onMounted(()=>{
    store.dispatch("uniModule/getInscriptionByFac", tokenToId(localStorage.getItem("accessToken")))
})
</script>

<style scoped>
.headerContainer{
    display: flex;
    justify-content: start;
}
.etuContainer{
    display: flex;
    gap: 10px;
    align-items: start;
}
.etuProps>*{
    margin:0;
    margin-bottom: 5px;
}
.etuProps>span{
    color: gray;
    font-weight: bold;
}
</style>