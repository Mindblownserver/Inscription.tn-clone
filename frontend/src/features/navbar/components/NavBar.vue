<template>
    <ul class="nav" style="right:100px ;left:100px">
        <a href="/" class="title">Inscription.tn</a>
        <ul class="middle" v-if="isStudent()">
          <li><router-link to="/student">Home</router-link></li>
          <li><router-link to="/student/payment">Payement</router-link></li>
          <li><router-link to="/student/profile">Profile</router-link></li>
          <li><router-link to="/faq">FAQ</router-link></li>
        </ul>
        <ul class="middle" v-else>
          <li><router-link to="/student">student</router-link></li>
          <li><router-link to="/university">university</router-link></li>
          <li><router-link to="/recipes/all">Recipes</router-link></li>
          <li><router-link to="/tricks/all">Tricks</router-link></li>
        </ul>
        
        <div class="right">
          <DropDownBtn v-model:userData="userData"/>
        </div>

    </ul>
    
</template>

<script setup lang="ts">
import { onMounted, ref } from "vue";
import DropDownBtn from "./DropDownBtn.vue"
import { useStore } from "vuex";
import { Student } from "@/features/student/utilities/interfaces";
import UserInfo from "@/service/authService";
import { tokenToId } from "@/service/tokenDecryptor";

const store = useStore();
const userData = ref<UserInfo>({
  id:"",
  name: ""
})

const isStudent = ()=>localStorage.getItem('role')=='student';
const isUniversity = ()=>localStorage.getItem("role")=="university";

onMounted(() => {
  if(tokenToId(localStorage.getItem("accessToken"))){
    if(isStudent()){
      console.log(tokenToId(localStorage.getItem("accessToken")))
      store.dispatch("studentModule/getStudentByCin", tokenToId(localStorage.getItem("accessToken"))).then(()=>{
          let studentInfo: Student = store.state.studentModule.student;
          console.log(studentInfo);
          userData.value.id = studentInfo.cin;
          userData.value.name = studentInfo.prenomFrEtu + " " + studentInfo.nomFrEtu
      }).catch(error=>{
        alert(error)
      })
    }else if(isUniversity()){
      console.log("Uni detected")
    }
  }else{
    console.error("Not logged in..yet :)")
  } 
})
</script>

<style scoped>
.nav {
  margin: 0;
  padding: 0;
  position: absolute;
  top: 0;
  right: 0;
  height: 48px;
  background-color: rgb(247, 247, 247);
  transition: 0.4s;
  left: 0;
}

a{
  cursor:pointer;
  text-decoration: none;
}

li a.router-link-exact-active::after {
  width: 100%;
}
li a.router-link-exact-active{
  color: rgb(63, 116, 230);
}

.middle{
  display: flex;
  margin-top: 10px;
  justify-content: center;
}

li {
    
    float: left;
    position: relative;
    text-align: center;
    border-bottom: 3px solid transparent;
    display: flex;
}

.middle li{
    padding: 10px 15px;
    margin-left: 35px;

}

.title{
  position: absolute;
  left: 0;
  bottom: 6px;
  background: linear-gradient(to right,var(--main-web-color) 80%,10%, rgba(255,0,0) );  
  background-clip: text;
  -webkit-text-fill-color: transparent;
  font-size: 24px;
  font-weight: bold;
  letter-spacing: 0.24px;
  margin-top: 10px;
  margin-left: 20px;
}


li a {
  font-size: 16px;
  display: block;
  color: black;
  text-decoration: none;
  transition: 0.3s ease-in-out;
}

li a::after{
  display: block;
  content: "";
  width: 0;
  height: 3px;
  margin: 0 auto;
  background-image: linear-gradient(to right, rgb(0, 33, 242), var(--main-web-color));
  border-radius: 5px;
  transition: 0.3s ease-in-out;
}

.middle a:hover::after{
  width: 100%;
}

.right{
  position: absolute;
  right: 0;
  top: 10px;
}
</style>