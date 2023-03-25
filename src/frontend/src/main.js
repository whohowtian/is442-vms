import { createApp } from 'vue'
import App from './App.vue'
import store from './store'
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import Vueform from '@vueform/vueform/plugin'
import vueformConfig from './../vueform.config'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import './index.scss'
import router from './router'
import './assets/styles/main.css'
import { VueDraggableNext } from 'vue-draggable-next'

import { library } from '@fortawesome/fontawesome-svg-core';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { faTrash, faEllipsis,faPenSquare,faEye } from '@fortawesome/free-solid-svg-icons'
import '@fortawesome/fontawesome-free/css/all.css';
import { far } from "@fortawesome/free-regular-svg-icons";
import VueDatePicker from '@vuepic/vue-datepicker';
import '@vuepic/vue-datepicker/dist/main.css'
import VueLodash from 'vue-lodash'

library.add(faTrash, faEllipsis,faPenSquare,faEye)
library.add(far);

const app = createApp(App)
app.component('font-awesome-icon', FontAwesomeIcon);
app.component('VueDatePicker', VueDatePicker);
app.component('draggable',VueDraggableNext);
app.use(Vueform, vueformConfig)
app.use(VueLodash)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
  }


app.use(store)

app.use(router)
app.use(ElementPlus)


app.mount('#app')
