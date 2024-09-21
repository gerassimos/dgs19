{{ define "pages/page1.tpl" }}
    {{ template "layouts/header.tpl" .}}
        <h1 class="card-title">t1</h1>
        <p class="card-text">t2</p>
    {{ template "layouts/footer.tpl" .}}
{{ end }}