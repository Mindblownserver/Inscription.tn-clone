<template>
    <Card>
        <template #header>
            <img class="cardImg" :src="image" alt="">
            <Button class="btn" :label="lbl" :severity="severity" raised />
            <h1 class="cardTitle">{{ btnLbl }}</h1>
        </template>
    </Card>
</template>
<script setup lang="ts">
import Card from 'primevue/card';
import Button from 'primevue/button';
const props = defineProps({
    image:{
        required:true,
        type: String
    },
    lbl:{
        required:true,
        type:String
    },
    severity:{
        required:true,
        type:String
    },
    btnLbl:{
        type:String,
        required:true
    }
})

</script>
<style scoped>
.cardImg{
    width: 100%;
    height: 350px;
    object-fit: fill;
    z-index: 1;
}
.btn{
    position: absolute;
    bottom: 10px;
    right: 20px;
}
.cardTitle{
    position: absolute;
    top: 0;
    left: 20px;
    color: black;
    font-weight: bolder;
    

}
</style>