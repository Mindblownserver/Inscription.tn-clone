<template>
    <section>
        <div class="img"><img src="../../../assets/images/University Life Illustration.jpg" alt="login cover art">
        </div>
        <div class="content">
            
            <div class="form">
                <h2>Login to Insription.tn</h2>
                <form>
                    <InputText class="formInput" v-model="cin" type="text" size="large" placeholder="Cin" :invalid="cinValidator.test(cin)" />
                    <br>
                    <InputText class="formInput" v-model="password" type="password" size="large" placeholder="Password" />
                    <br>
                    <div class="formElement">
                        <p>Don't have an account? <router-link to="/signup">Sign up</router-link></p>
                    </div>
                    <Button class="btn" label="Login" severity="info" raised @click="handleLogin"/>
                </form>
            </div>
        </div>
    </section>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import InputText from 'primevue/inputtext';
import Button from 'primevue/button';
import { myApi } from '@/service/MyApi';
import { useRouter } from 'vue-router';
import { useToast } from 'primevue/usetoast';
import { showError, showSuccess } from '@/service/myToastService';

const cin = ref("");
const cinValidator = new RegExp("^$")
const toast = useToast();
const password = ref("");

const router = useRouter()

const handleLogin = async () => {
    try {
        if(!cinValidator.test(cin.value)){
            await myApi.login(cin.value, password.value);
            showSuccess(toast,"Login success", "You'll be redirected to your home page")
            
            router.push({path:'/'+localStorage.getItem("role")})
        }
    } catch (error) {
    console.error('Login failed:', error);
    showError(toast, "Login failed", "Your username and/or password are/is wrong")
    }
};



</script>

<style scoped>
*{
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}
section{
    position: relative;
    width: 100%;
    height: 98vh;
    display: flex;
}
section .img{
    position: relative;
    width: 40%;
    height: 100%;
}
section .img:before{
    content: "";
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-image: linear-gradient(to left, var(--main-web-color) 0%, rgb(56, 103, 246) 100%);
    z-index: 1;
    mix-blend-mode: screen;
}

section .img img{
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    object-fit: cover;
}

section .content{
    display: flex;
    justify-content: center;
    align-items: center;
    width: 50%;
    height: 100%;
}
section .content .form{
    width: 50%;
    display: block;
}
h2{
    color: black;
    font-weight: bolder;
    font-size: 1.5rem;
    margin-bottom: 20px;
    display: inline-block;
    letter-spacing: 1px;
}
.formElement, .formInput{
    margin-block: 20px;
}

.formInput{
    width: 100%;
    padding: 10px 20px;
    outline: none;
    font-weight: 400;
    border: 1px solid rgb(39, 108, 228);
    font-size: 16px;
    letter-spacing: 1px;
    color: rgb(64, 64, 64);
    background: transparent;
}

.btn{
    color: white;
    font-weight: 500;
    width: 50%;
    padding: 10px 20px;
}

@media  (max-width: 900px) {
    section .img {
        position: absolute;
        top: 0;
        left: 0;
        width: 100%;
        height: 100%;
    }
    section .content{
        display: flex;
        justify-content: center;
        align-items: center;
        width: 100%;
        height: 100%;
        z-index: 1;
    }
    section .content .form{
        width: 100%;
        padding: 40px;
        background-color: rgb(255 255 255 / 0.9);
        margin: 50px;
        display: block;
    }
}
</style>