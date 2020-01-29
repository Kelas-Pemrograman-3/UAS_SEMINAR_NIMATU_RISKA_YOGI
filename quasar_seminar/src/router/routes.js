
const routes = [
  {
    path: '/',
    component: () => import('layouts/guest.vue'),
    children: [
      { path: '', component: () => import('pages/login.vue') }
    ]
  },
  {
    path: '/home',
    component: () => import('layouts/MyLayout.vue'),
    children: [
      { path: '', component: () => import('pages/Index.vue') }
    ]
  },
  {
    path: '/dataseminar',
    component: () => import('layouts/MyLayout.vue'),
    children: [
      { path: '', component: () => import('pages/dataseminar.vue') }
    ]
  },
  {
    path: '/insertpeserta',
    component: () => import('layouts/MyLayout.vue'),
    children: [
      { path: '', component: () => import('pages/daftarpeserta.vue') }
    ]
  },
  {
    path: '/datapeserta',
    component: () => import('layouts/MyLayout.vue'),
    children: [
      { path: '', component: () => import('pages/datapeserta.vue') }
    ]
  }
]
// Always leave this as last one

if (process.env.MODE !== 'ssr') {
  routes.push({
    path: '/inputdata',
    component: () => import('pages/Index.vue')
  })
}
export default routes
