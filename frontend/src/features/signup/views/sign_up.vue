<template>
    <section>
        <div class="img"><img src="../../../assets/images/University-Students-Illustration.jpg" alt="">
        </div>
        <div class="content">
            <div class="form">
                <h2>Sign up to Inscription.tn</h2>
                <form>  
                    <InputText class="formInput" v-model="cin" type="text" size="large" placeholder="CIN" :invalid="!cinValidator.test(cin)" />
                    <br>
                    <InputText class="formInput" v-model="password" type="password" size="large" placeholder="Password" />
                    <br>
                    <InputText class="formInput" v-model="confirmPassword" type="password" size="large" placeholder="Confirm password" />
                    
                    <div class="formElement">
                        <p>Have an account? <a href="/login">Login</a></p>
                    </div>
                    <Button class="btn" label="Sing up" severity="info" raised @click="handleRegister"/>
                </form>
            </div>
        </div>
    </section>
</template>
<script setup>
import { ref } from 'vue';
import InputText from 'primevue/inputtext';
import Button from 'primevue/button';
import { myApi } from '@/service/MyApi';
import { useRouter } from 'vue-router';

//const email = ref("");
//const emailValidator = new RegExp("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
const password = ref("");
const confirmPassword = ref("");
const cin = ref("");

const cinValidator = new RegExp("^\\d{8}$")

const router = useRouter();

const handleRegister = async () => {
    try {
        console.log()
        if(cinValidator.test(cin.value) && confirmPassword.value==password.value){
            await myApi.register(cin.value, password.value);
            alert('Registration successful!');
            router.push({path:'/login'})
        }
        else
            alert("cin or password wrong")
    } catch (error) {
    console.error('Registration failed:', error);
    alert('Registration failed!');
    }
};


</script>

<style scoped>
*{
    margin: 0;
    padding: 0;
    box-sizing: border-box;
}
.form-check{
    margin:2.5px;
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
    background-image: linear-gradient(to left, var(--main-web-color) 0%, rgba(248, 117, 55, .8) 100%);
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
}
section .content .form h2{
    color: black;
    font: 600;
    font-size: 1.5rem;
    font-weight: bolder;
    margin-bottom: 20px;
    display: inline-block;
    letter-spacing: 1px;
}

section .content .form .remember{
    margin-bottom: 10px;
    color: var(--main-web-color);
    font-weight: 400;
    font-size: 14px;
}
section .content .form h4{
    color:var(--main-web-color);
    text-align: center;
    margin: 80px 0 10px;
    font-weight: 500;
    
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
    }
    section .content .form .h3{
        color: rgb(228, 99, 39);
        text-align: center;
        margin: 30px 0 10px;
        font-weight: 500;
    }
}
</style>