<script>
import {doGet, doPost} from "../http/httpRequest.js";
import {getTokenName, messageTip, removeTokens} from "../util/util.js";

export default {
  name: "LoginView",

  data() {
    return {
      user : {},
      loginRules: {
        loginAct: [
          { required: true, message: 'Please input username', trigger: 'blur' }
        ],
        loginPwd: [
          {required: true, message: 'Please input password', trigger: 'blur'},
          { min: 6, max: 16, message: 'Length should be 6 to 16', trigger: 'blur' }
        ]
      }
    }
  },

  mounted() {
    this.freeLogin();
  },

  methods: {
    freeLogin() {
      console.log("free login")
      let token = window.localStorage.getItem(getTokenName());
      if (token) {
        doGet("api/login/free", {}).then(res => {
          console.log(res);
          if (res.data.code === 200) {
            window.location.href = "/dashboard"
          }
        });
      }
    },

    login() {
      // Validate form before submitting
      this.$refs.loginFormRef.validate(isValid => {
        if (isValid) {
          let formData = new FormData();
          formData.append('loginAct', this.user.loginAct);
          formData.append('loginPwd', this.user.loginPwd);
          formData.append('rememberMe', this.user.rememberMe);
          doPost("/api/login", formData).then(res => {
            console.log(res);
            if (res.data.code === 200) {
              messageTip('登录成功', 'success');

              // Delete old tokens
              removeTokens();

              // Store the jwt
              if (this.user.rememberMe === true) {
                window.localStorage.setItem(getTokenName(), res.data.data);
              } else {
                window.sessionStorage.setItem(getTokenName(), res.data.data);
              }
              // Route to the app main page
              window.location.href = "/dashboard";
            } else {
              messageTip('登录失败', 'error');
            }
          });
        }
      });
    }
  }
}
</script>

<template>

  <el-container>
    <el-aside width="200px">
      <img src="../assets/loginBox.svg">
      <p class="imgTitle">
        欢迎使用动力云客系统
      </p>
    </el-aside>

    <el-main>
      <div class="loginTitle">欢迎登录</div>

      <el-form ref="loginFormRef" :model="user" label-width="120px" :rules="loginRules">

        <el-form-item label="Username" prop="loginAct">
          <el-input v-model="user.loginAct" />
        </el-form-item>

        <el-form-item label="Password" prop="loginPwd">
          <el-input type="password" v-model="user.loginPwd" show-password/>
        </el-form-item>

        <el-form-item>
          <el-button type="primary" @click="login()">Login</el-button>
        </el-form-item>

        <el-form-item>
          <el-checkbox label="Remember me" v-model="user.rememberMe"></el-checkbox>
        </el-form-item>

      </el-form>
    </el-main>
  </el-container>

</template>

<style scoped>
.el-aside {
  background-color: black;
  width: 40%;
  text-align: center;
}

.el-main {
  height: calc(100vh);
}

img {
  height: 400px;
}

.imgTitle {
  color: white;
  font-size: 28px;
}

.el-form {
  width: 60%;
  margin: auto;
}

.loginTitle {
  text-align: center;
  margin-top: 100px;
  margin-bottom: 15px;
  font-weight: bold;
}

.el-button {
  width: 100%;
}
</style>