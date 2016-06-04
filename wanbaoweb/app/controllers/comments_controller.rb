class CommentsController < ApplicationController
	skip_before_filter :verify_authenticity_token
	def index
		@latest_comments = Comment.all.reverse
	end
	def m_create
		title = params["title"]
		text = params["text"]
		Comment.create(title: title, text: text)
		render status: 200, nothing: true
	end
	def m_view
		title = params["title"]
		num = params["num"].to_i
		comment = Comment.where(title: title).last(num).first
		if comment && num <= Comment.where(title: title).count
			render status: 200, json: {text: comment.text, time: comment.created_at}.to_json
		else
			render status: 200, json: {text: "fail"}.to_json
		end
	end
	def m_remove
		title = params["title"]
		num = params["num"].to_i
		comment = Comment.where(title: title).last(num).first
		if comment
			comment.destroy
			render status: 200, json: {removed: true}.to_json
		else
			render status: 200, json: {removed: false}.to_json
		end
	end
end
