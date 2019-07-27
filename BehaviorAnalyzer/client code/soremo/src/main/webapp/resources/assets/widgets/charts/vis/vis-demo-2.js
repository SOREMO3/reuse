var nodes = null;
var edges = null;
var network = null;


$(document).ready(function () {
    // create people.
    // value corresponds with the age of the person
    nodes = [{
        id: 1,
        value: 30,
        label: '연구 계획서',
        group: 'g1level1',
        fixed: true,
        font: {
            size: 10
        },
        title: '<h3>ACM 연구정리1</h3><p></p><table id="datatable-fixedcolumns" class="table table-hover">' +
            '<tr><th>Creater</th><td>&nbsp;홍길동</td></tr>' +
            '<tr><th>Last modified with application</th><td>&nbsp;Microsoft word docx</td></tr>' +
            '<tr><th>Created Date</th><td>&nbsp;2016-08-23 00:33:02</td>' +
            '</table>'
                    }, {
        id: 2,
        value: 30,
        label: 'SRS',
        group: 'g2level1',
        fixed: true,
        font: {
            size: 10
        }
                    }, {
        id: 3,
        value: 10,
        label: 'ACM 연구정리2',
        group: 'g1level2',
        font: {
            size: 10
        }
                    }, {
        id: 4,
        value: 30,
        label: '연차보고서',
        group: 'g3level1',
        fixed: true,
        font: {
            size: 10
        }
                    }, {
        id: 5,
        value: 20,
        label: 'RD 구성 및 관계유형 논의사항 2',
        group: 'g1level2',
        font: {
            size: 10
        }
                    }, {
        id: 6,
        value: 10,
        label: 'RD 구성 및 관계유형 논의사항',
        group: 'g3level2',
        font: {
            size: 10
        }
                    }, {
        id: 8,
        value: 10,
        label: 'RD 종류 및 구성요소',
        group: 'g2level2',
        font: {
            size: 10
        }
                    }, {
        id: 9,
        value: 10,
        label: 'g2RD Rem간의 추론규칙 정리',
        group: 'g2level2',
        font: {
            size: 10
        }
                    }, {
        id: 10,
        value: 20,
        label: 'Node Level 2',
        group: 'g3level2',
        font: {
            size: 10
        }
                    }];

    // create connections between people
    // value corresponds with the amount of contact between two people
    edges = [{
        from: 2,
        to: 8,
        
                    }, {
        from: 2,
        to: 9,
        
                    }, {
        from: 4,
        to: 6,
        
                    }, {
        from: 4,
        to: 10,
        
                    }, {
        from: 1,
        to: 5,
        
                    }, {
        from: 1,
        to: 3,
        
                    }];

    // Instantiate our network object.
    var container = document.getElementById('mynetwork');
    var data = {
        nodes: nodes,
        edges: edges
    };
    var options = {
        layout: {
            hierarchical: {
                direction: 'UD'
            }
        },
        nodes: {
            borderWidth: 2,
            shape: 'dot',
            scaling: {
                label: {
                    min: 8,
                    max: 20
                }
            }
        },
        interaction: {
            hover: true
        },
        groups: {
            g1level1: {
                shape: 'icon',
                icon: {
                    face: 'FontAwesome',
                    code: '\uf0f6',
                    size: 50,
                    color: '#3498db'
                }
            },
            g1level2: {
                shape: 'icon',
                icon: {
                    face: 'FontAwesome',
                    code: '\uf0f6',
                    size: 30,
                    color: '#59bafb'
                }
            },
            g2level1: {
                shape: 'icon',
                icon: {
                    face: 'FontAwesome',
                    code: '\uf0f6',
                    size: 50,
                    color: '#af982b'
                }
            },
            g2level2: {
                shape: 'icon',
                icon: {
                    face: 'FontAwesome',
                    code: '\uf0f6',
                    size: 30,
                    color: '#af982b'
                }
            },
            g3level1: {
                shape: 'icon',
                icon: {
                    face: 'FontAwesome',
                    code: '\uf0f6',
                    size: 50,
                    color: '#00b29c'
                }
            },
            g3level2: {
                shape: 'icon',
                icon: {
                    face: 'FontAwesome',
                    code: '\uf0f6',
                    size: 30,
                    color: '#00b29c'
                }
            }
        },
        manipulation: {
            addEdge: function (data, callback) {
                data.arrows = 'to';
                data.label = '충분 관계';
                if (data.from == data.to) {
                   
                    var r = confirm("Do you want to connect the node to itself?");
                    if (r == true) {
                        callback(data);
                    }
                } else {
                    callback(data);
                }
            }
        }
    };

    network = new vis.Network(container, data, options);
    network.on("click", function (params) {
        var data = JSON.stringify(params, null, 4);
        params.event = "[original event]";
        console.log('<h2>Click event:</h2>' + data);
        //$('body').append('<button class="btn btn-primary" style="position:absolute;z-index:9999;top:'+params.pointer.DOM.y+'px;left:'+params.pointer.DOM.x+'px">의미기반 RD 테스트</button>');
    });
});
