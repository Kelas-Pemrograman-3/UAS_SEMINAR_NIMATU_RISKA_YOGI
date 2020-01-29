<template>
  <q-layout view="lHh Lpr lFf">
    <q-header elevated>
      <q-toolbar>
        <q-btn
          flat
          dense
          round
          @click="leftDrawerOpen = !leftDrawerOpen"
          icon="menu"
          aria-label="Menu"
        />

        <q-toolbar-title>
        SEMINAR
        </q-toolbar-title>

        <div>Quasar v{{ $q.version }}</div>
      </q-toolbar>
    </q-header>

    <q-drawer
      v-model="leftDrawerOpen"
      show-if-above
      bordered
      content-class="bg-grey-2"
    >
      <q-list>
        <q-item-label header>MENU</q-item-label>
        <q-item clickable tag="a" target="_blank" href="http://localhost:8080/dataseminar">
          <q-item-section>
            <q-item-label>Data Seminar</q-item-label>
          </q-item-section>
        </q-item>
        <q-item clickable tag="a" target="_blank" href="http://localhost:8080/insertpeserta">
          <q-item-section>
            <q-item-label>Input data peserta</q-item-label>
          </q-item-section>
        </q-item>
          <q-item clickable tag="a" target="_blank" href="http://localhost:8080/datapeserta">
          <q-item-section>
            <q-item-label>Data Peserta</q-item-label>
             </q-item-section>
        </q-item>
          <q-item clickable tag="a" target="_blank" href="http://localhost:8080/">
          <q-item-section>
            <q-item-label>Keluar</q-item-label>
          </q-item-section>
        </q-item>

      </q-list>
    </q-drawer>

    <q-page-container>
      <router-view />
    </q-page-container>
  </q-layout>
</template>

<script>
export default {
  name: 'MyLayout',

  data () {
    return {
      leftDrawerOpen: false
    }
  }
}
</script>
