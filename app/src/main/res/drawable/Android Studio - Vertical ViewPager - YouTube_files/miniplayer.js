(function(g){var window=this;var W4=function(a){g.OC.call(this,{B:"div",G:"ytp-miniplayer-ui"});this.player=a;this.H=!1;this.F=this.A=this.l=void 0;this.K(a,"minimized",this.FJ);this.K(a,"onStateChange",this.DM)},X4=function(a){g.yK.call(this,a);
this.g=new W4(this.player);this.g.hide();g.kK(this.player,this.g.element,4);a.app.F.g&&(this.load(),g.K(a.getRootNode(),"ytp-player-minimized",!0))};
g.t(W4,g.OC);g.h=W4.prototype;
g.h.show=function(){this.l=new g.Zn(this.OF,null,this);this.l.start();if(!this.H){this.D=new g.UV(this.player,this);g.G(this,this.D);g.kK(this.player,this.D.element,4);this.D.A=.6;this.O=new g.YU(this.player);g.G(this,this.O);this.C=new g.W({B:"div",G:"ytp-miniplayer-scrim"});g.G(this,this.C);this.C.g(this.element);this.K(this.C.element,"click",this.bz);var a=new g.W({B:"button",V:["ytp-miniplayer-close-button","ytp-button"],L:{"aria-label":"\u0417\u0430\u0442\u0432\u043e\u0440\u0438"},J:[g.pP()]});
g.G(this,a);a.g(this.C.element);this.K(a.element,"click",this.Ax);this.u=new g.W({B:"div",G:"ytp-miniplayer-controls"});g.G(this,this.u);this.u.g(this.C.element);this.K(this.u.element,"click",this.bz);var b=new g.W({B:"div",G:"ytp-miniplayer-button-container"});g.G(this,b);b.g(this.u.element);a=new g.W({B:"div",G:"ytp-miniplayer-play-button-container"});g.G(this,a);a.g(this.u.element);var c=new g.W({B:"div",G:"ytp-miniplayer-button-container"});g.G(this,c);c.g(this.u.element);this.M=new g.$S(this.player,
this,!1);g.G(this,this.M);this.M.g(b.element);b=new g.XS(this.player,this);g.G(this,b);b.g(a.element);this.I=new g.$S(this.player,this,!0);g.G(this,this.I);this.I.g(c.element);this.F=new g.sU(this.player,this);g.G(this,this.F);this.F.g(this.C.element);this.A=new g.fT(this.player,this);g.G(this,this.A);g.kK(this.player,this.A.element,4);this.o=new g.W({B:"div",G:"ytp-miniplayer-buttons"});g.G(this,this.o);g.kK(this.player,this.o.element,4);this.o.hide();a=new g.W({B:"button",V:["ytp-miniplayer-close-button",
"ytp-button"],L:{"aria-label":"\u0417\u0430\u0442\u0432\u043e\u0440\u0438"},J:[g.pP()]});g.G(this,a);a.g(this.o.element);this.K(a.element,"click",this.Ax);a=new g.W({B:"button",V:["ytp-miniplayer-replay-button","ytp-button"],L:{"aria-label":"\u0417\u0430\u0442\u0432\u043e\u0440\u0438"},J:[g.BP()]});g.G(this,a);a.g(this.o.element);this.K(a.element,"click",this.BK);this.K(this.player,"presentingplayerstatechange",this.PF);this.K(this.player,"appresize",this.Wa);this.K(this.player,"fullscreentoggled",
this.Wa);this.Wa();this.H=!0}0!=this.player.getPlayerState()&&g.OC.prototype.show.call(this);this.A.show();this.player.unloadModule("annotations_module")};
g.h.hide=function(){this.l&&(this.l.dispose(),this.l=void 0);g.OC.prototype.hide.call(this);this.player.app.F.g||(this.H&&this.A.hide(),this.player.loadModule("annotations_module"))};
g.h.U=function(){this.l&&(this.l.dispose(),this.l=void 0);g.OC.prototype.U.call(this)};
g.h.Ax=function(){this.player.stopVideo();this.player.la("onCloseMiniplayer")};
g.h.BK=function(){this.player.playVideo()};
g.h.bz=function(a){if(a.target==this.C.element||a.target==this.u.element)g.Q(g.X(this.player).experiments,"kevlar_miniplayer_scrim_pause")?2==this.player.getPlayerState()?this.player.playVideo():this.player.pauseVideo():this.player.la("onExpandMiniplayer")};
g.h.FJ=function(){g.K(this.player.getRootNode(),"ytp-player-minimized",this.player.app.F.g);this.player.app.F.g&&0==this.player.getPlayerState()?this.o.show():this.o.hide()};
g.h.OF=function(){this.A.Kc();this.F.Kc();this.l&&this.l.start()};
g.h.PF=function(a){g.U(a.state,32)&&this.D.hide()};
g.h.Wa=function(){var a=this.A,b=g.XJ(this.player).getPlayerSize().width;a.xa=0;a.A=b;a.D=!1;g.hT(a)};
g.h.DM=function(a){this.player.app.F.g&&(0==a?(this.hide(),this.o.show()):(this.show(),this.o.hide()))};
g.h.Za=function(){return this.D};
g.h.ac=function(){return!1};
g.h.wg=function(){return!1};
g.h.hi=function(){return!1};
g.h.Gs=function(){};
g.h.Wg=function(){};
g.h.uj=function(){};
g.h.en=function(){return null};
g.h.ir=function(){return new g.nh(0,0,0,0)};
g.h.handleGlobalKeyDown=function(){return!1};
g.h.handleGlobalKeyUp=function(){return!1};
g.h.Dn=function(a,b,c,d,e){var f=0,k=d=0,l=g.Sh(a);if(b){c=g.Rn(b,"ytp-prev-button")||g.Rn(b,"ytp-next-button");var m=g.Rn(b,"ytp-play-button");c?f=k=12:m&&(b=g.Ph(b,this.element),k=b.x,f=b.y-12)}else k=c-l.width/2,d=25+(e||0);b=g.XJ(this.player).getPlayerSize().width;e=f+(e||0);k=g.qd(k,0,b-l.width);e?(a.style.top=e+"px",a.style.bottom=""):(a.style.top="",a.style.bottom=d+"px");a.style.left=k+"px"};
g.h.showControls=function(){};
g.h.jr=function(){};
g.h.Ph=function(){};g.t(X4,g.yK);X4.prototype.init=function(){};
X4.prototype.load=function(){this.player.hideControls();this.g.show()};
X4.prototype.unload=function(){this.player.showControls();this.g.hide()};g.UU.miniplayer=X4;})(_yt_player);
