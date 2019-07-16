import mixpanel from 'mixpanel-browser'
mixpanel.init('78a42ccba0e9a55de00c30b454c5da8e');

export function track(event, props) {
	mixpanel.track(event, props)
}

if (!window.localStorage.getItem('_known_user')) {
	window.localStorage.setItem('_known_user', 'true')
	fetch('https://hooks.slack.com/services/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', {
		method: 'POST',
		headers: {
		},
		body: JSON.stringify({text: 'New User with mixpanel ID: '+ mixpanel.persistence.props.distinct_id })
	})
}