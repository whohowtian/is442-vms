import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'LoginView',
      component: () => import('../views/LoginView.vue')
    },
    {
      path: '/AdminView',
      name: 'AdminView',
      component: () => import('../views/AdminView.vue')
    },
    {
      path: '/AccountView',
      name: 'AccountView',
      component: () => import('../views/AccountView.vue')
    },
    {
      path: '/WorkflowView',
      name: 'WorkflowView',
      component: () => import('../views/WorkflowView.vue')
    },
    {
      path: '/VendorView',
      name: 'VendorView',
      component: () => import('../views/VendorView.vue')
    },
    {
      path: '/ApprovalView',
      name: 'ApprovalView',
      component: () => import('../views/ApprovalView.vue')
    },
    {
      path: '/CreateAccount',
      name: 'CreateAccount',
      component: () => import('../views/CreateAccount.vue')
    },
    {
      path: '/ActivateAccount',
      name: 'ActivateAccount',
      component: () => import('../views/ActivateAccount.vue')
    },
    {
      path: '/FormBuilder',
      name: 'FormBuilder',
      component: () => import('../views/form/index.vue')
    },
    {
      path: '/VendorForm',
      name: 'VendorForm',
      component: () => import('../views/form/VendorForm.vue')
    },
    {
      path: '/EditAccount',
      name: 'EditAccount',
      component: () => import('../views/EditAccount.vue')
    },
    {
      path: '/UpdateFormBuilder',
      name: 'UpdateFormBuilder',
      component: () => import('../views/form/AdminUpdate/index.vue')
    },
    {
      path: '/tryApi',
      name: 'tryApi',
      component: () => import('../tryApi.vue')
    },
  ]
})

export default router
