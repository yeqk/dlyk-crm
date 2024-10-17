import { createApp } from 'vue'

import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

import LoginView from "./view/LoginView.vue";

createApp(LoginView).use(ElementPlus).mount('#app')
