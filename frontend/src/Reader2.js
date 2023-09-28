import React, { Component } from "react";
import { render } from "react-dom";

class Reader2 extends React.Component {



    constructor(props) {
        super(props);
        this.state = {vvv: 'oj', sum: 0};
        this.eventSource = undefined;
    }

    componentDidMount() {
         this.setState({
              vvv: 'bamboleo'
            });

            this.eventSource = new EventSource("http://localhost:8080/stream");
            this.eventSource.onmessage = (event) => {
                    console.log(event.data)

                    this.setState(prevState => {
                           return {
                           vvv: event.data,
                           sum: prevState.sum + 141
                           }
                        });

//                             this.setState({
//                                  vvv: event.data,
//                                  sum:
//                                });
            }
            this.eventSource.onerror = (err) => {
                console.error("EventSource failed:", err);
                this.eventSource.close();
            }
      }

    render() {
        return <div>Click {this.state.vvv}---{this.state.sum}</div>;
    }
}

export default Reader2;