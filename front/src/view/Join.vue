<template>
    <Header/>

    <h1>Join 페이지</h1>

    <div>
        <input type="text" id="id" v-model="id" placeholder="아이디를 입력해주세요.">
    </div>
    <span>{{ checkEmptyId }}</span>
    <br>
    <div>
        <input type="text" id="pw" v-model="pw" placeholder="비밀번호를 입력해주세요.">
    </div>
    <span>{{ checkEmptyPw }}</span>
    <br>
    <div>
        <input type="text" id="re-pw" @change="checkPw()" v-model="rePw" placeholder="비밀번호를 한번 더 입력해주세요.">
    </div>
    <span>{{ checkPwMsg }}</span>
    <span>{{ checkEmptyRePw }}</span>
    <br>
    <div>
        <input type="text" id="name" v-model="name" placeholder="이름을 입력해주세요.">
    </div>
    <span>{{ checkEmptyName }}</span>
    <br>
    <div>
        <input type="text" id="email" v-model="email" placeholder="이메일을 입력해주세요.">
    </div>
    <span>{{ checkEmptyEmail }}</span>
    <br>
    <div>
        <input type="text" id="tel" v-model="tel" placeholder="연락처를 입력해주세요.">
    </div>
    <span>{{ checkEmptyTel }}</span>
    <br>
    <div>
        <button type="button" @click="join">Join</button>
    </div>
</template>

<script setup>
import Header from "@/layout/Header.vue";
import axios from "axios";
import { ref } from 'vue';

let id = ref('');
let pw = ref('');
let rePw = ref('');
let name = ref('');
let email = ref('');
let tel = ref('');

let checkEmptyId = ref('');
let checkEmptyPw = ref('');
let checkEmptyRePw = ref('');
let checkEmptyName = ref('');
let checkEmptyEmail = ref('');
let checkEmptyTel = ref('');

let checkPwMsg = ref('');

function checkPw() {
    if(pw.value != rePw.value) {
        checkEmptyRePw.value = '';
        checkPwMsg.value = '비밀번호가 일치하지 않습니다.';
        return false;
    } else {
        checkPwMsg.value = '';
        return true;
    }
};

let join = async () => {

    if(checkEmpty() && checkPw()) {
        try {
            let response = await axios.post('/api/joinProc', {
                id: id.value,
                pw: pw.value,
                rePw: rePw.value,
                name: name.value,
                email: email.value,
                tel: tel.value
            });
            console.log(response.data);
        } catch (error) {
            console.error(error);
        }
    }
};

function checkEmpty() {
    if(id.value == '') {
        checkEmptyId.value = '아이디를 입력해주세요.'
        return false;
    } else {
        checkEmptyId.value = ''
    }
    if(pw.value == '') {
        checkEmptyPw.value = '비밀번호를 입력해주세요.'
        return false;
    } else {
        checkEmptyPw.value = ''
    }
    if(rePw.value == '') {
        checkEmptyRePw.value = '비밀번호를 재입력해주세요.'
        return false;
    } else {
        checkEmptyRePw.value = ''
    }
    if(name.value == '') {
        checkEmptyName.value = '이름을 입력해주세요.'
        return false;
    } else {
        checkEmptyName.value = ''
    }
    if(email.value == '') {
        checkEmptyEmail.value = '이메일을 입력해주세요.'
        return false;
    } else {
        checkEmptyEmail.value = ''
    }
    if(tel.value == '') {
        checkEmptyTel.value = '연락처를 입력해주세요.'
        return false;
    } else {
        checkEmptyTel.value = ''
    }
    return true;
}

</script>

<style>
</style>