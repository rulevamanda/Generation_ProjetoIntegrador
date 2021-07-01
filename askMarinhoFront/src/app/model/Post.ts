import { Comment } from "./Comment"
import { Report } from "./Report"
import { Tag } from "./Tag"
import { Upvote } from "./Upvote"
import { User } from "./User"

export class Post {
    public idPost: number
    public title: string
    public description: string
    public urlImage: string
    public date: Date
    public comment: Comment[]
    public userPost: User
    public tagRelation: Tag[]
    public reported: Report
    public upvoted: Upvote
}