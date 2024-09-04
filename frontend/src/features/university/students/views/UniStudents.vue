<template>
    <div id="container">
        <Card id="tableWidget">
            <template #content>
                <StudentsTable />
            </template>
        </Card>
    </div>
</template>

<script setup lang="ts">
import StudentsTable from '../components/StudentsTable.vue';
import Card from 'primevue/card';
</script>

<style scoped>
#container{
    display: grid;
    grid-template-columns:  112px 1fr 100px ;
    padding-top: 20px;
}
#tableWidget{
    grid-row: 1/1;
    grid-column: 2/3;
    min-width: 0;
}

</style>