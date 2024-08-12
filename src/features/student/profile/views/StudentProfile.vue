<template>
    <div class="profileContainer">
        <div class="left">
            <h2>Additional information</h2>
            <Form />
        </div>
        
        <Divider class="divider" layout="vertical" />
        
        <div class="right">
            <h2>Preview</h2>
            <CarteEtudiantVerif />
        </div>
    </div>
</template>

<script setup lang="ts">
import CarteEtudiantVerif from '../../components/CarteEtudiantVerif.vue';
import Divider from 'primevue/divider';
import Form from "../components/Form.vue"

</script>

<style scoped>
.profileContainer{
    display: grid;
    grid-template-columns: 112px 1fr 50px 1fr 100px;
}

.right{
    position: sticky;
    grid-column: 4/5;
    grid-row: 1/2;
    justify-self: end;
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