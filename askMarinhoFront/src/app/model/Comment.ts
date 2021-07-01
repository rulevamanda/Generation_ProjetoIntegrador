import { Post } from "./Post"
import { Report } from "./Report"
import { Upvote } from "./Upvote"
import { User } from "./User"

export class Comment {
    public idComment: number
    public text: string
    public userComment: User
    public post: Post
    public reported: Report
    public upvoted: Upvote
}