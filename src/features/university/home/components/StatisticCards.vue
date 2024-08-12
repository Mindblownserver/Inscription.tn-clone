<template>
    <div id="container">
        <Card class="item1">
            <template #header>  
                <div class="iconHolder">
                    <i class="material-icons-round opacity-10 fs-5 icon">person</i>
                </div>
            </template>
            <template #title>
                <div class="title">
                    <span >
                        This Year's Registered students
                    </span>
                </div>
                <span class="subtitle">
                    1983
                </span>
            </template>
            <template #footer>
                <Divider />
                <div class="footer">
                    <i class="material-icons-round opacity-10 fs-5 trend">trending_up</i>
                    <span> 30% more than last year's</span>
                </div>
            </template>
        </Card>

        <Card class="item2">
            <template #header>  
                <div class="iconHolder">
                    <i class="material-icons-round opacity-10 fs-5 icon">calendar_month</i>
                </div>
            </template>
            <template #title>
                <div class="title">
                    <span >
                        Payment Periode
                    </span>
                </div>
                <span class="subtitle" style="color: rgb(152, 0, 0);">
                    Not opened
                </span>
            </template>
            <template #footer>
                <Divider />
                <div class="footer">
                    <i class="material-icons-round opacity-10 fs-5 notOpened">error</i>
                    <span>Open the registration before the deadline</span>
                </div>
            </template>
        </Card>

        <Card class="item3">
            <template #header>  
                <div class="iconHolder">
                    <i class="material-icons-round opacity-10 fs-5 icon">schedule</i>
                </div>
            </template>
            <template #title>
                <div class="title">
                    <span >
                        The website will close in
                    </span>
                </div>
                <span class="subtitle">
                    01/10/2024
                </span>
            </template>
            <template #footer>
                <Divider />
                <div class="footer">
                    <i class="material-icons-round opacity-10 fs-5 info">info</i>
                    <span> It will reopen for reorientation</span>
                </div>
            </template>
        </Card>
    </div>
</template>
<script setup lang="ts">
import Card from 'primevue/card';
import Divider from 'primevue/divider';
</script>

<style scoped>
#container{
    display: grid;
    grid-template-columns:  112px 1fr 1fr 1fr 100px ;
    padding-top: 20px;
}
.item1, .item2, .item3{
    width: 24rem;
    position: relative;
    text-align: end;
}

.item1{
    grid-row: 1/1;
    grid-column: 2/3;
}
.item2{
    grid-row: 1/1;
    grid-column: 3 / 4;
    justify-self: center;
}
.item3{
    grid-row: 1/1;
    grid-column: 4/5;
    justify-self: end;
}

.iconHolder{
  position: absolute;
  top: -24px;
  left: 16px;
  width: 48px;
  height: 48px;
  border-radius: 10px;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 24px;
  background: linear-gradient(to right, rgb(33, 138, 243), rgb(14, 109, 233)); 
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
}
.icon{
    color: white;

}

.cardBody{
    padding-top: 16px;
}
.title {
  font-weight: 600;
  font-size: 16px;
  margin-bottom: 5px;
}
.subtitle {
  font-size: 20px;
  font-weight: bolder;
  margin: 0;
  color: black;
}
.footer{
    display: flex;
    justify-content: end;
    align-items: center;
    gap: 10px;
}
.trend{
    color: rgb(22, 110, 22);
    background-color: rgba(37, 206, 37, 0.43);
    padding: 5px;
    border-radius: 50%;
}

.notOpened{
    color: rgb(152, 0, 0);
    background-color: rgba(230, 76, 76, 0.734);
    padding: 5px;
    border-radius: 50%;
}

.info{
    color: rgb(0, 48, 152);
    background-color: rgba(76, 130, 230, 0.734);
    padding: 5px;
    border-radius: 50%;
}
</style>