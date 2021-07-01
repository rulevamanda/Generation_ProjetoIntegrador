import { Comment } from "./Comment"
import { Post } from "./Post"
import { User } from "./User"

export class Upvote {
    public idUpvote: number
    public userUpvote: User[]
    public postUpvote: Post
    public commentUpvote: Comment
}