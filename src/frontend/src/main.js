import { createApp } from 'vue'
import App from './App.vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import router from './router'
import './assets/styles/main.css'

import { library } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { faTrash, faEllipsis,faPenSquare,faEye } from '@fortawesome/free-solid-svg-icons'
import '@fortawesome/fontawesome-free/css/all.css';

library.add(faTrash, faEllipsis,faPenSquare,faEye)

const app = createApp(App)
app.component('font-awesome-icon', FontAwesomeIcon);

app.use(router)
app.use(ElementPlus)
app.mount('#app')
