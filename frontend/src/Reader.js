import React, { useEffect, useState } from 'react'

const Reader = () => {

    const [listening, setListening] = useState(false);

    let eventSource = undefined;

    useEffect(() => {
        if (!listening) {
            eventSource = new EventSource("http://localhost:8080/stream");
            eventSource.onmessage = (event) => {
                    console.log(event.data)
            }
            eventSource.onerror = (err) => {
                console.error("EventSource failed:", err);
                eventSource.close();
            }
            setListening(true)
        }
        return () => {
                eventSource.close();
                console.log("event closed")
        }

    }, [])

    return (
        <div>
8888888888888
        </div>
    )
}

export default Reader;