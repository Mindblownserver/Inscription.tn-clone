<template>
    <div class="container">
        <TimeCard class="timeCard"/>
        <StatisticCard class="totalStudents" title="Total Registered Students" 
        value="127 543" detail="30 more than last year's" icon="trending_up"/>
        <StatisticCard class="totalUni" title="Total Registered Institutes & Faculties"
        value="528" detail="28 are private and 500 are public" icon="info"/>
    </div>
</template>
<script setup lang="ts">
import TimeCard from "../components/TimeCard.vue"
import StatisticCard from "../components/StatisticCard.vue"
</script>

<style scoped>
.container{
    display: grid;
    grid-template-columns:  112px 1fr 1fr 100px ;
    grid-template-rows: 130px 170px;
    padding-top: 20px;
    row-gap: 50px;
}
.timeCard{
    grid-row: 1/2;
    grid-column: 2/3;
}
.totalStudents{
    grid-row: 2/3;
    grid-column: 2/3;
}
.totalUni{
    grid-row: 2/3;
    grid-column: 3/4;
}
</style>