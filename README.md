<article class="markdown-body entry-content" itemprop="text">
  <p>
    <a href="https://github.com/iotoasis/SO/blob/master/logo_oasis_m.png" target="_blank">
      <img src="https://github.com/iotoasis/SO/blob/master/logo_oasis_m.png" alt="Logo" style="max-width:100%;">
     </a>
  </p>

<h1><a id="user-content-oasis-project" class="anchor" href="#oasis-project" aria-hidden="true"><svg aria-hidden="true" class="octicon octicon-link" height="16" version="1.1" viewBox="0 0 16 16" width="16"><path fill-rule="evenodd" d="M4 9h1v1H4c-1.5 0-3-1.69-3-3.5S2.55 3 4 3h4c1.45 0 3 1.69 3 3.5 0 1.41-.91 2.72-2 3.25V8.59c.58-.45 1-1.27 1-2.09C10 5.22 8.98 4 8 4H4c-.98 0-2 1.22-2 2.5S3 9 4 9zm9-3h-1v1h1c1 0 2 1.22 2 2.5S13.98 12 13 12H9c-.98 0-2-1.22-2-2.5 0-.83.42-1.64 1-2.09V6.25c-1.09.53-2 1.84-2 3.25C6 11.31 7.55 13 9 13h4c1.45 0 3-1.69 3-3.5S14.5 6 13 6z"></path></svg></a>Oasis Project</h1>

<p>본 Oasis (Open-source Architecture Semantic Iot Service-platform) 프로젝트는 국제 표준을 준용하는 오픈 소스 기반 지능형 사물 인터넷 서비스 플랫폼을 개발하는 것을 목표로 하고 있습니다.</p>

<p>본 Oasis 프로젝트는 2015년도 정보통신․방송 기술개발사업 신규지원 대상과제 "(ICBMS-3세부) 사물 가상화, 분산 자율지능 및 데이터 연계/분석을 지원하는 IoT 기반 플랫폼 기술 개발" 과제의 결과물로써 오픈소스로 제공됩니다.</p>

<p>본 Oasis 프로젝트는 오픈 소스 커뮤니티를 기반으로 오픈소스로써 계속적으로 성장해 나갈 계획입니다.</p>

<h1><a id="user-content-so-service-orchestration-framework" class="anchor" href="#so-service-orchestration-framework" aria-hidden="true"><svg aria-hidden="true" class="octicon octicon-link" height="16" version="1.1" viewBox="0 0 16 16" width="16"><path fill-rule="evenodd" d="M4 9h1v1H4c-1.5 0-3-1.69-3-3.5S2.55 3 4 3h4c1.45 0 3 1.69 3 3.5 0 1.41-.91 2.72-2 3.25V8.59c.58-.45 1-1.27 1-2.09C10 5.22 8.98 4 8 4H4c-.98 0-2 1.22-2 2.5S3 9 4 9zm9-3h-1v1h1c1 0 2 1.22 2 2.5S13.98 12 13 12H9c-.98 0-2-1.22-2-2.5 0-.83.42-1.64 1-2.09V6.25c-1.09.53-2 1.84-2 3.25C6 11.31 7.55 13 9 13h4c1.45 0 3-1.69 3-3.5S14.5 6 13 6z"></path></svg></a>IN-AE</h1>

<p>IN-AE는 사용자 앱으로, IoT GW에 연결된 Z-Wave센서(Door Sensor, Door lock, Plug, wall switch, gas lock)의 상태 정보 확인 및 센서를 제어할 수 있고, IoT GW의 상태 정보를 확인 할 수 있다. 또한, IoT Device에 연결된 경광등/가로등의 상태 정보를 확인하고 제어를 할 수 있다. 모든 데이터는 oneM2M 서버(IN-CSE)를 통해 처리한다.</p>

<h2><a id="user-content-주요-기능" class="anchor" href="#주요-기능" aria-hidden="true"><svg aria-hidden="true" class="octicon octicon-link" height="16" version="1.1" viewBox="0 0 16 16" width="16"><path fill-rule="evenodd" d="M4 9h1v1H4c-1.5 0-3-1.69-3-3.5S2.55 3 4 3h4c1.45 0 3 1.69 3 3.5 0 1.41-.91 2.72-2 3.25V8.59c.58-.45 1-1.27 1-2.09C10 5.22 8.98 4 8 4H4c-.98 0-2 1.22-2 2.5S3 9 4 9zm9-3h-1v1h1c1 0 2 1.22 2 2.5S13.98 12 13 12H9c-.98 0-2-1.22-2-2.5 0-.83.42-1.64 1-2.09V6.25c-1.09.53-2 1.84-2 3.25C6 11.31 7.55 13 9 13h4c1.45 0 3-1.69 3-3.5S14.5 6 13 6z"></path></svg></a>주요 기능</h2>

<h3>IoT Gateway</h3>

<ul>
<li>IoT Gateway DM 정보 확인</li>
<li>Z-Wave Device 상태정보 확인</li>
<li>Z-Wave Device 상태 제어</li>
<li>Z-Wave Device DM 정보 확인</li>
</ul>

<h3>IoT Device</h3>

<ul>
<li>IoT Device DM 정보 확인</li>
<li>oneM2M 서버를 통한 가로등/경광등 제어</li>
<li>BLE 신호를 통한 가로등/경광등 제어</li>
</ul>
