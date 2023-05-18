<template>
    <Header/>
    <div class="contents">
        <h1>회원 가입</h1>
        <br>
        <div>
            <div class="contents-row">
                <input type="text" id="id" v-model="id" @change="() => {checkIdState = false;}" placeholder="아이디를 입력해주세요.">
                <button type="button" @click="checkId()">아이디 확인</button>
            </div>
            <div>
                <span>{{ checkEmptyId }}</span>
                <span>{{ checkIdMsg }}</span>
            </div>
        </div>
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
            <button class="btn-join" type="button" @click="join">Join</button>
        </div>
    </div>
</template>

<script setup>
import Header from "@/layout/Header.vue";
import axios from "axios";
import { ref } from 'vue';
import router from "@/router";

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

let checkIdState = ref(false);
let checkIdMsg = ref('');
let checkPwMsg = ref('');

async function checkId() {
    if(id.value == '') {
        checkEmptyId.value = '아이디를 입력해주세요.'
        return false;
    } else {
        checkEmptyId.value = ''
    }
    try {
        let response = await axios.post('./api/user/checkId', {
            id: id.value
        });
       console.log(response);
       if(response.data.code == "0000") {
            checkIdMsg.value = '사용 가능한 아이디 입니다.'
            checkIdState.value = true;
       } else {
            checkIdMsg.value = '이미 존재하는 아이디 입니다.'
            checkIdState.value = false;
       }
    } catch(err) {
       console.log(err);
    }
}

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

    if(checkIdState.value) {
        if(checkEmpty() && checkPw()) {
            try {
                let response = await axios.post('/api/user/joinProc', {
                    id: id.value,
                    pw: pw.value,
                    rePw: rePw.value,
                    name: name.value,
                    email: email.value,
                    tel: tel.value
                });
                if(response.data.code == "0000") {
                    alert('회원가입에 성공하셨습니다.');
                    await router.push('/login');
                } else {
                    alert('예기치 못한 에러가 발생했습니다.');
                }
            } catch (error) {
                console.error(error);
            }
        }
    } else {
        alert('아이디 중복 여부를 체크해주세요.');
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

<style scoped>

.contents {
    text-align: center;
    margin-top: 5%;
}

.contents-row {
    display: flex;
    justify-content: center;
}

.contents input {
    width: 13%;
    margin-right: 1%;
}

.contents-row button {
    width: 5%;
}

.btn-join {
    width: 10%;
    margin-right: 1%;
}

</style>