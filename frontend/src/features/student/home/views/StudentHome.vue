<template>
    <div class="container">
            
        <Card class="item1">
            <template #header>
                <img class="cardImg" src="../../../../assets/images/paymentCards.png" alt="">
                <Button class="btn" @click="router.push({name:'StudentPayment'})" label="View Payment" severity="success" raised />
                <h1 class="cardTitle">Payment</h1>
            </template>
        </Card>

        <!-- Usmanabce https://www.vecteezy.com/vector-art/26103040-profile-vector-icon-design -->
        <Card class="item2">
            <template #header>
                <img class="cardImg" src="../../../../assets/images/profileCard.jpg" alt="">
                <Button class="btn" @click="router.push({name:'StudentProfile'})" label="View Profile" severity="secondary" raised />
                <h1 class="cardTitle">Profile</h1>
            </template>
        </Card>

        <Card class="item3">
            <template #header>
                <img class="cardImg" src="../../../../assets/images/ContactUsCard.png" alt="">
                <Button class="btn" label="Contact us" severity="info" raised />
                <h1 class="cardTitle">Contact us</h1>
            </template>
        </Card>

        <Card class="item4">
            <template #header>
                <img class="cardImg" src="../../../../assets/images/FAQCard.png" alt="">
                <Button class="btn" label="FAQ" severity="help" raised @click="router.push({name:'FAQ'})"/>
                <h1 class="cardTitle">View FAQ</h1>
            </template>
        </Card>
        <!-- from djvstock https://www.vecteezy.com/vector-art/2471079-student-man-with-glasses-and-bag-character-cartoon-people-student-university -->
        <img class="item5" src="../../../../assets/images/student.png" alt="">
    </div>
    
</template>

<script setup lang="ts">
import Card from 'primevue/card';
import Button from 'primevue/button';
import { useRouter } from 'vue-router';

const router = useRouter();


</script>


<style scoped>
.container{
    display: grid;
    grid-template-rows: 100px 350px 10vh 350px;
    grid-template-columns: 112px 1fr 5em 1fr 100px;
    
}
.item1,.item2,.item3,.item4{
    overflow: hidden;
    position: relative;
}

.item1{
    grid-row: 2/3;
    grid-column: 2/3;
    
}
.item2{
    grid-row: 2/3;
    grid-column: 4/5;
}
.item3{
    grid-row: 4/5;
    grid-column: 2/3;
}
.item4{
    grid-row: 4/5;
    grid-column: 4/5;
}

.item5{
    grid-row: 3/4;
    grid-column: 3/4;
    height: 200px;
    width:200px;
    justify-self: center;
    align-self: center;
    border-radius: 50%;
    z-index: 1;
    
}
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