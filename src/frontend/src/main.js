import { createApp } from 'vue'
import App from './App.vue'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import Vueform from '@vueform/vueform/plugin'
import vueformConfig from './../vueform.config'
import './index.scss'
import router from './router'
import './assets/styles/main.css'

import { library } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { faTrash, faEllipsis,faPenSquare,faEye } from '@fortawesome/free-solid-svg-icons'
import '@fortawesome/fontawesome-free/css/all.css';
import { far } from "@fortawesome/free-regular-svg-icons";
import VueDatePicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css'

library.add(faTrash, faEllipsis,faPenSquare,faEye)
  library.add(far);

const app = createApp(App)
app.component('font-awesome-icon', FontAwesomeIcon);
app.component('VueDatePicker', VueDatePicker);
app.use(Vueform, vueformConfig)

app.use(router)
app.use(ElementPlus)


app.mount('#app')
